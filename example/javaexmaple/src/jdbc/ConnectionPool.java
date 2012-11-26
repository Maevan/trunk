package jdbc;

import java.io.FileInputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 连接池
 * 
 * @author lv9
 * @since 2010.09.27 13:42
 */
final public class ConnectionPool {
	private LinkedList<PoolConnection> poolConnections = new LinkedList<PoolConnection>();// 代理连接链表

	private Integer poolSize;// 当前连接总数

	private static ConnectionPool pool = null;// 连接池
	private AdditionalConnection additionalConnection;// 补充连接方法
	private final ConnectionPoolProperties poolProperties;// 连接池配置信息

	private ConnectionPool() throws Exception {
		FileInputStream in = null;
		try {
			in = new FileInputStream(getClass().getResource("").getPath() + "database.properties");// database.properties
																									// 连接池配置信息文件
			poolProperties = new ConnectionPoolProperties(in);

			init();// 调用初始化方法
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * 执行更新SQL语句 返回更新记录数
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:40
	 * @param sql
	 * @return Integer 更新记录数
	 * @throws Exception
	 */
	public Integer executeUpdate(String sql) throws Exception {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();// 获得连接
			statement = connection.createStatement();

			return statement.executeUpdate(sql);
		} finally {
			close(statement, connection);
		}
	}

	/**
	 * 执行查询SQL语句 返回查询记录 <br/>
	 * 如果查询记录返回的结果集只有一列 返回的是List&lt;Object&gt; 否则返回List&lt;Object[]&gt;
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @param sql
	 * @return List<?> 查询结果 一个持有Object[]或Object类型的集合
	 * @throws Exception
	 */
	public List<?> executeQuery(String sql) throws Exception {
		List<?> result = null;

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			try {
				connection = getConnection();
				statement = connection.createStatement();
				rs = statement.executeQuery(sql);

				ResultSetMetaData metaData = rs.getMetaData();

				int columnCount;

				if ((columnCount = metaData.getColumnCount()) > 1) {
					result = getSingleList(rs);
				} else {
					result = getList(rs, columnCount);
				}
			} finally {
				close(rs, statement, connection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 封装结果集(封装多列结果集)
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @param rs
	 *            结果集
	 * @param columnCount
	 *            列数
	 * @return List&lt;Object[]&gt; 返回结果
	 * @throws Exception
	 */
	private List<Object[]> getList(ResultSet rs, final int columnCount) throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();

		while (rs.next()) {
			Object[] objs = new Object[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				objs[i - 1] = rs.getObject(i);
			}
			list.add(objs);
		}
		return list;
	}

	/**
	 * 封装结果集(封装单列结果集)
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @param rs
	 *            结果集
	 * @return List&lt;Object&gt; 返回结果
	 * @throws Exception
	 */
	private List<Object> getSingleList(ResultSet rs) throws Exception {
		List<Object> list = new ArrayList<Object>();

		while (rs.next()) {
			list.add(rs.getObject(1));
		}
		return list;
	}

	/**
	 * 获得连接池
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @return ConnectionPool 连接池
	 * @throws Exception
	 */
	public static synchronized ConnectionPool getConnectionPool() throws Exception {
		if (pool == null) {
			pool = new ConnectionPool();
		}
		return pool;
	}

	/**
	 * 获得代理连接池连接
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @return Connection 代理连接池连接
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		Connection connection = null;

		synchronized (poolConnections) {
			if (!poolConnections.isEmpty()) {
				connection = poolConnections.removeFirst().getProxyConnection();
			}
		}
		connection = connection != null ? connection : additionalConnection.getAdditionalConnection();
		return connection;
	}

	/**
	 * 关闭连接(不带结果集)
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @param statement
	 *            语句对象
	 * @param connection
	 *            连接池连接
	 */
	public void close(Statement statement, Connection connection) {
		close(null, statement, connection);
	}

	/**
	 * 关闭连接
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @param rs
	 * @param statement
	 * @param connection
	 */
	public void close(ResultSet rs, Statement statement, Connection connection) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得连接池配置信息
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @return ConnectionPoolProperties 连接池配置信息
	 */
	protected ConnectionPoolProperties getPoolProperties() {
		return poolProperties;
	}

	/**
	 * 获得当前全部连接数量
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @return int 全部连接数量
	 */
	protected int getPoolSize() {
		return poolSize;
	}

	/**
	 * 获得当前连接池空闲连接数量
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @return int 当前连接池空闲连接数量
	 */
	protected int getFreeConnectionCount() {
		return poolConnections.size();
	}

	/**
	 * 删除连接
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 */
	protected void removeAConnection() {
		synchronized (poolSize) {
			poolSize--;
		}
	}

	/**
	 * 重置连接(具体操作根据补充连接方式来决定)
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @param poolConnection
	 */
	protected void resetConnection(PoolConnection poolConnection) {
		additionalConnection.reset(poolConnection);
	}

	/**
	 * 初始化连接池
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @throws Exception
	 */
	private void init() throws Exception {
		Class.forName(poolProperties.getDriverName());

		this.poolSize = poolProperties.getInitPoolSize();

		// 是否等待空闲连接
		if (poolProperties.isWaitingFreeConnection()) {
			additionalConnection = new WaitConnection();// 当连接不够的时候 等待连接
		} else {
			additionalConnection = new TemporaryConnection();// 当连接不够的时候 创建临时连接
		}
		// 创建连接
		for (int i = 0; i < poolSize; i++) {
			poolConnections.addFirst(addProxyConnection());
		}
	}

	/**
	 * 添加代理连接
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @return PoolConnection 代理连接
	 * @throws Exception
	 */
	private PoolConnection addProxyConnection() throws Exception {
		return new PoolConnection(getDataBaseConnection());
	}

	/**
	 * 添加数据库连接
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 * @return Connection 数据库连接
	 * @throws Exception
	 */
	private Connection getDataBaseConnection() throws Exception {
		return DriverManager.getConnection(poolProperties.getConnectionURL(), poolProperties.getUserName(), poolProperties.getPassword());
	}

	@Override
	public String toString() {
		return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "当前总连接数:" + getPoolSize() + ". 当前空闲连接数:" + getFreeConnectionCount();
	}

	/**
	 * 补充连接
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 */
	public abstract class AdditionalConnection {
		/**
		 * 获得补充连接
		 * 
		 * @since 2010.09.27 13:42
		 * @return Connection 连接池连接
		 * @throws Exception
		 */
		public abstract Connection getAdditionalConnection() throws Exception;

		/**
		 * 重置连接
		 * 
		 * @since 2010.09.27 13:42
		 * @param connection
		 *            代理连接
		 */
		public abstract void reset(PoolConnection connection);
	}

	/**
	 * 补充连接(用临时连接)
	 * 
	 * @since 2010.09.27 13:42
	 * @author lv9
	 */
	private class TemporaryConnection extends AdditionalConnection {

		@Override
		public Connection getAdditionalConnection() throws Exception {
			return addProxyConnection().getProxyConnection();
		}

		@Override
		public void reset(PoolConnection poolConnection) {
			synchronized (poolConnections) {
				poolConnections.addLast(poolConnection);
			}
		}

	}

	/**
	 * 补充连接(等待使用完毕的连接)
	 * 
	 * @since 2010.09.27 13:42
	 * @author lv9
	 * 
	 */
	private class WaitConnection extends AdditionalConnection {

		private LinkedList<WaitConnectionObject> requestQueue = new LinkedList<WaitConnectionObject>();// 等待连接请求链表

		@Override
		public Connection getAdditionalConnection() throws Exception {
			WaitConnectionObject waitObject = new WaitConnectionObject();

			synchronized (requestQueue) {
				requestQueue.addLast(waitObject);
			}
			synchronized (waitObject) {
				if (waitObject.getConnection() == null) {
					System.err.println(waitObject.toString() + ": 开始等待连接 " + this.toString());
					waitObject.wait();
				}
			}
			System.err.println(waitObject.toString() + ": 获得等待连接 " + this.toString());
			return waitObject.getConnection().getProxyConnection();
		}

		@Override
		public void reset(PoolConnection poolConnection) {
			WaitConnectionObject waitConnectionObject = null;
			synchronized (requestQueue) {
				if (!requestQueue.isEmpty()) {
					waitConnectionObject = requestQueue.removeFirst();
				}
			}

			if (waitConnectionObject != null) {
				synchronized (waitConnectionObject) {
					waitConnectionObject.setConnection(poolConnection);
					waitConnectionObject.notify();

					return;
				}
			} else {
				synchronized (poolConnections) {
					poolConnections.addLast(poolConnection);
				}
			}
		}

		@Override
		public String toString() {
			return "当前连接等待队列中有 " + requestQueue.size() + " 个请求";
		}

		private class WaitConnectionObject {
			private PoolConnection connection;

			public PoolConnection getConnection() {
				return connection;
			}

			public void setConnection(PoolConnection connection) {
				this.connection = connection;
			}

			@Override
			protected void finalize() throws Throwable {
				System.err.println("当前等待连接对象已经被回收");
			}
		}
	}

	/**
	 * 代理连接
	 * 
	 * @author lv9
	 * @since 2010.09.27 13:42
	 */
	private class PoolConnection implements InvocationHandler {
		private final Connection proxyConnection; // 连接池连接

		private Connection connection;// 数据库连接
		private long lastUpdateConnectionTime;// 上次更新时间

		public PoolConnection(Connection connection) {
			proxyConnection = (Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] { Connection.class }, this);// 创建代理连接对象

			this.connection = connection;
			this.lastUpdateConnectionTime = System.currentTimeMillis();// 初始化更新时间
		}

		/**
		 * 返回连接池连接
		 * 
		 * @author lv9
		 * @since 2010.09.27 13:42
		 * @return Connection 连接池连接
		 */
		public Connection getProxyConnection() {
			return proxyConnection;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			// 判断当前连接是否过期 如果过期则将该代理连接内部的数据库连接更新
			if ((System.currentTimeMillis() - lastUpdateConnectionTime) > poolProperties.getConnectionUpdateInterval()) {
				try {
					connection.close();
				} catch (Exception exception) {
					System.err.println("连接关闭失败");
				}

				connection = getDataBaseConnection();
				lastUpdateConnectionTime = System.currentTimeMillis();
				System.out.println("连接更新成功 间隔时间为:" + poolProperties.getConnectionUpdateInterval() / 1000 / 60 + "分钟");
			}

			if (method.getName().trim().equals("close")) {
				// 如果当前空闲连接数小于最大连接 则重置该连接 否则将该连接释放
				if (getFreeConnectionCount() <= poolProperties.getMaxConnectionsSize()) {
					resetConnection(this);
				} else {
					method.invoke(connection, args);
					removeAConnection();
				}
				return null;
			} else {
				return method.invoke(connection, args);
			}
		}

		@Override
		protected void finalize() throws Throwable {
			System.err.println("当前连接池对象已经被回收");
		}
	}
}
