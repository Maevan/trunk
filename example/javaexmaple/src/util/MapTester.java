package util;

import java.util.Iterator;
import java.util.LinkedList;

public class MapTester {
	public static void main(String[] args) throws InterruptedException {
		final MyMap<Integer, String> map = new MyMap<Integer, String>();

		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000 * 4);
					map.remove(0);
				} catch (Exception e) {}
			}
		}.start();

		for (int i = 0; i < 30; i++) {
			map.put(i, Integer.toBinaryString(i));
			map.get(i % 2);
		}

	}
}

@SuppressWarnings("unchecked")
class MyMap<K, V> {
	public static int DEFAULT_CAPACITY = 20;
	public static long DEFAULT_EXPIRATION = 1000 * 10;
	private int capacity;
	private int size;
	private LinkedList[] entries;
	private LinkedList<RegisterObject> queue;
	private long expiration;

	public MyMap() {
		this(DEFAULT_CAPACITY);
	}

	public MyMap(int capacity) {
		this(capacity, DEFAULT_EXPIRATION);
	}

	public MyMap(int capacity, long expiration) {
		this.capacity = capacity - capacity / 3;
		this.entries = new LinkedList[capacity];
		this.queue = new LinkedList<RegisterObject>();
		this.expiration = expiration;
		this.size = 0;
	}

	public synchronized void put(K key, V value) {
		if (isFull()) {
			RegisterObject registerObject = null;

			while (registerObject == null) {
				registerObject = queue.removeFirst();

				if (!registerObject.isValid()) {
					registerObject = null;
				}
			}

			long timeLeft = registerObject.getTimeLeft();
			if (timeLeft > 0) {
				try {
					System.err.println("已经的到达最大容量 等待清理过期元素");
					wait(timeLeft);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			clear();
		}
		int index = getIndex(key);

		if (entries[index] == null) {
			entries[index] = new LinkedList();
		}
		LinkedList list = entries[index];
		for (Iterator it = list.iterator(); it.hasNext();) {
			if (((ElementObject) it.next()).getKey().hashCode() == key.hashCode()) {
				it.remove();
				break;
			}
		}

		size++;
		list.addLast(new ElementObject(key, value, queue));
	}

	public synchronized V get(K key) {
		ElementObject result = search(key);

		if (result != null) {
			long timeout = result.getRegisterObject().getTimeLeft();

			if (timeout > 0) {
				clearCreateTime(result);
				return (V) result.getValue();
			} else {
				remove(key);
			}
		}
		return null;
	}

	public synchronized V remove(K key) {
		int index = getIndex(key);
		LinkedList list = entries[index];
		ElementObject elementObject = null;

		if (list != null) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				elementObject = (ElementObject) it.next();
				if (elementObject.getKey().hashCode() == key.hashCode()) {
					it.remove();
					elementObject.getRegisterObject().setValid(false);
					System.err.println("删除元素 key:" + elementObject.getKey() + ";value:" + elementObject.getValue());
					notify();
					size--;
					return (V) elementObject.getValue();
				}
			}
		}

		return null;
	}

	private synchronized ElementObject search(K key) {
		int index = getIndex(key);
		LinkedList list = entries[index];
		ElementObject result = null;

		if (list != null) {
			for (Object obj : list) {
				if (((ElementObject) obj).getKey().hashCode() == key.hashCode()) {
					result = (ElementObject) obj;
				}
			}
		}

		return result;
	}

	private int getIndex(K key) {
		return Math.abs(key.hashCode() % capacity);
	}

	private synchronized boolean isFull() {
		clear();
		return size == DEFAULT_CAPACITY;
	}

	private synchronized void clear() {
		for (Iterator<RegisterObject> it = queue.iterator(); it.hasNext();) {
			RegisterObject registerObject = it.next();
			if (!registerObject.isValid()) {
				it.remove();
			} else if (registerObject.getTimeLeft() <= 0) {
				it.remove();
				remove((K) registerObject.getKey());
			} else {
				return;
			}
		}
	}

	private void clearCreateTime(ElementObject elementObject) {
		RegisterObject oldRegisterObject = elementObject.getRegisterObject();
		RegisterObject newRegisterObject = new RegisterObject(elementObject.getKey());
		elementObject.setRegisterObject(newRegisterObject);

		queue.add(newRegisterObject);
		oldRegisterObject.setValid(false);
	}

	class RegisterObject {
		private long createTime;
		private boolean isValid;

		private Object key;

		public RegisterObject(Object key) {
			this(System.currentTimeMillis(), key);
		}

		public RegisterObject(long createTime, Object key) {
			this(createTime, true, key);
		}

		public RegisterObject(long createTime, boolean isValid, Object key) {
			this.createTime = createTime;
			this.isValid = isValid;
			this.key = key;
		}

		public long getTimeLeft() {
			return this.createTime + expiration - System.currentTimeMillis();
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public boolean isValid() {
			return isValid;
		}

		public void setValid(boolean isValid) {
			this.isValid = isValid;
		}

		public Object getKey() {
			return key;
		}

		public void setKey(Object key) {
			this.key = key;
		}

	}

	class ElementObject {
		private Object key;
		private Object value;
		private RegisterObject registerObject;

		public ElementObject() {

		}

		public ElementObject(Object key, Object value, LinkedList<RegisterObject> queue) {
			this.key = key;
			this.value = value;
			this.registerObject = new RegisterObject(key);

			queue.addLast(registerObject);
		}

		public Object getKey() {
			return key;
		}

		public void setKey(Object key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public RegisterObject getRegisterObject() {
			return registerObject;
		}

		public void setRegisterObject(RegisterObject registerObject) {
			this.registerObject = registerObject;
		}
	}
}
