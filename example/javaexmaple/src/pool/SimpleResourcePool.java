package pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import pool.exceptions.ClosedException;
import pool.exceptions.GetResException;

public class SimpleResourcePool<T extends PoolResource> {
	public T get() {
		return get(-1);// 不阻塞
	}

	public T get(long time) {
		if (!isInit) {
			waitInit();
		}

		if (isClosed) {
			throw new ClosedException("连接池已经关闭");
		}
		waits.addAndGet(1);
		Res res = null;

		while (true) {
			res = unused.poll();

			if (res == null) {
				synchronized (count) {
					if (count.intValue() + acquireIncrement <= maxPoolSize) {// 检查是否有必要补充资源
						int toadd = maxPoolSize - count.intValue() > acquireIncrement ? acquireIncrement : maxPoolSize - count.intValue();
						count.addAndGet(toadd);
						res = add(toadd);
						break;
					}
				}
				if (time > 0) {
					try {
						// 如果无资源 等待队列两秒返回资源
						for (int i = 0; i++ < retryCount && (res = unused.poll(time, TimeUnit.MILLISECONDS)) == null;)
							break;
					} catch (InterruptedException e) {}
				}

				if (res == null) {
					waits.addAndGet(-1);
					throw new GetResException("资源获取失败");
				}
			}

			if (res.isDestory) {
				continue;
			}

			if (res.isWaitCheck) {
				synchronized (checking) {
					checking.put(res, true);
				}
				continue;
			}

			break;
		}
		waits.addAndGet(-1);
		checkedout.put(res, true);
		res.isCheckedout = true;
		return res.itarget;
	}

	public void init() {
		count.addAndGet(initPoolSize);
		for (int i = 0; i < initPoolSize; i++) {
			unused.add(new Res(factory.generateResource()));
		}

		if (checkidleResourcesDelay > 0) {
			scheduledExecutor.scheduleAtFixedRate(new CheckResTask(), checkidleResourcesDelay, checkidleResourcesDelay, TimeUnit.SECONDS);
		}

		if (maxIdleTime > 0) {
			scheduledExecutor.scheduleAtFixedRate(new ClearExpiredResTask(), maxIdleTime, maxIdleTime, TimeUnit.SECONDS);
		}

		if (maxUseTime > 0) {
			scheduledExecutor.scheduleAtFixedRate(new ClearExpiredCheckoutTask(), maxIdleTime, maxIdleTime, TimeUnit.SECONDS);
		}

		isInit = false;
	}

	private Res add(int size) {
		if (size - 1 > 0) {
			scheduledExecutor.submit(new AddRes(size - 1));
		}
		return new Res(factory.generateResource());
	}

	public void close() {
		isClosed = true;
		try {
			checkLock.lock();
			while (waits.get() > 0)
				try {
					TimeUnit.SECONDS.sleep(5);
					System.err.println("剩余请求者数" + waits.get());
				} catch (InterruptedException e) {}

			for (Res res : unused) {
				res.destory();
				count.addAndGet(-1);
			}
			for (Res res : checkedout.keySet()) {
				res.destory();
				count.addAndGet(-1);
			}
			for (Res res : checking.keySet()) {
				res.destory();
				count.addAndGet(-1);
			}

			executor.shutdown();
			scheduledExecutor.shutdown();
		} finally {
			checkLock.unlock();
		}
	}

	private void waitInit() {
		initLock.lock();
		try {
			initCondition.await();
		} catch (InterruptedException e) {}
		initLock.unlock();
	}

	public int getCount() {
		return count.intValue();
	}

	public int getUnused() {
		return unused.size();
	}

	public int getCheckouted() {
		return checkedout.size();
	}

	final LinkedBlockingQueue<Res> unused = new LinkedBlockingQueue<Res>();
	final ConcurrentHashMap<Res, Boolean> checkedout = new ConcurrentHashMap<Res, Boolean>();
	final ConcurrentHashMap<Res, Boolean> checking = new ConcurrentHashMap<Res, Boolean>();
	final ConcurrentHashMap<Res, Boolean> all = new ConcurrentHashMap<Res, Boolean>();

	final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(3);// 定时执行
	final ExecutorService executor = Executors.newFixedThreadPool(5);// 异步任务执行
	final PoolResourceFactory<T> factory;

	final int initPoolSize;// 初始化连接数
	final int corePoolSize;// 最小连接数
	final int maxPoolSize;// 最大连接数
	final int acquireIncrement;// 当连接池中的连接耗尽的时候获取的连接数
	final int retryCount;// 获取连接尝试次数
	final long checkidleResourcesDelay; // 检查连接有效间隔时间
	final long maxIdleTime; // 空闲连接的最大存活时间
	final long maxUseTime;// 被签出的连接最大的使用时间

	volatile boolean isInit = true;
	volatile boolean isClosed = false;

	final Lock initLock = new ReentrantLock();
	final Lock checkLock = new ReentrantLock();
	final Condition initCondition = initLock.newCondition();
	final AtomicInteger count = new AtomicInteger();
	final AtomicInteger waits = new AtomicInteger();

	static final ClassLoader CLASS_LOADER = SimpleResourcePool.class.getClassLoader();

	public SimpleResourcePool(PoolResourceFactory<T> factory, int initPoolSize, int corePoolSize, int maxPoolSize, int acquireIncrement, long checkidleResourcesDelay, long maxIdleTime,
			long maxUseTime, int retryCount) {
		assert corePoolSize < initPoolSize;
		assert maxPoolSize - acquireIncrement > corePoolSize;

		this.factory = factory;
		this.initPoolSize = initPoolSize;
		this.corePoolSize = corePoolSize;
		this.maxPoolSize = maxPoolSize;
		this.acquireIncrement = acquireIncrement;
		this.checkidleResourcesDelay = checkidleResourcesDelay;
		this.maxIdleTime = maxIdleTime;
		this.maxUseTime = maxUseTime;
		this.retryCount = retryCount;
		init();
	}

	class AddRes extends Thread {
		public AddRes(int resCount) {
			this.resCount = resCount;
		}

		@Override
		public void run() {
			if (!isClosed && !isInterrupted()) {
				try {
					checkLock.lock();
					for (int i = 0; i < resCount; i++)
						unused.add(new Res(factory.generateResource()));
				} finally {
					checkLock.unlock();
				}
			}
		}

		private int resCount;
	}

	class CheckResTask extends Thread {
		@Override
		public void run() {
			if (isClosed || isInterrupted()) {
				return;
			}
			try {
				checkLock.lock();
				for (final Res res : all.keySet()) {
					if (res.isShouldCheck()) {
						continue;
					}
					res.isWaitCheck = true;
					executor.submit(new Runnable() {
						@Override
						public void run() {
							if (!res.target.check()) {
								res.destory();
							}

						}
					});
				}
			} finally {
				checkLock.unlock();
			}
		}
	}

	class ClearExpiredResTask extends Thread {
		@Override
		public void run() {
			if (!isClosed && !isInterrupted()) {
				try {
					checkLock.lock();
				} finally {
					checkLock.unlock();
				}
			}
		}
	}

	class ClearExpiredCheckoutTask extends Thread {
		@Override
		public void run() {
			if (!isClosed && !isInterrupted()) {
				try {
					checkLock.lock();
				} finally {
					checkLock.unlock();
				}
			}
		}
	}

	class Res {
		@SuppressWarnings("unchecked")
		public Res(final T target) {
			this.target = target;
			this.itarget = (T) Proxy.newProxyInstance(CLASS_LOADER, target.getClass().getInterfaces(), new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					if (method.getName().equals("close")) {
						close();
						return null;
					} else {
						Res.this.lastUseTime = System.currentTimeMillis();
						return method.invoke(target, args);
					}
				}
			});
		}

		public void destory() {
			count.addAndGet(-1);
			all.remove(this);
			target.close();
			isDestory = true;
		}

		public void close() {
			checkedout.remove(this);
			if (count.get() < corePoolSize) {
				isCheckedout = false;
				unused.add(this);
			} else {
				destory();
			}
		}

		public boolean isShouldCheck() {
			return System.currentTimeMillis() - lastCheckTime > checkidleResourcesDelay;
		}

		public boolean isExpired() {
			return System.currentTimeMillis() - lastUseTime > maxIdleTime;
		}

		volatile boolean isWaitCheck = false;
		volatile boolean isCheckedout = false;
		volatile boolean isDestory = false;
		volatile long lastUseTime = System.currentTimeMillis();
		volatile long lastCheckTime = System.currentTimeMillis();
		final T target;
		final T itarget;
	}
}
