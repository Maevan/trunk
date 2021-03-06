package jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConnectionPoolProperties {

	private String userName; // 用户名
	private String password;// 密码
	private String driverName;// 驱动
	private String connectionURL;// 连接地址
	private String databaseName;// 驱动
	private int maxConnectionsSize;// 最大连接
	private int initPoolSize;// 初始化连接
	private long connectionUpdateInterval;// 连接更新间隔时间
	private boolean isWaitingFreeConnection;// 是否等待空闲连接 默认为false 如果是的话

	// 当连接池没有连接的时候 等待回收的连接

	public ConnectionPoolProperties(FileInputStream in) {
		Properties properties = new Properties();

		try {
			properties.load(in);

			this.userName = properties.getProperty("userName");
			this.password = properties.getProperty("password");
			this.driverName = properties.getProperty("driverName");
			this.connectionURL = properties.getProperty("connectionURL");
			this.databaseName = properties.getProperty("databaseName");
			this.maxConnectionsSize = Integer.parseInt(properties
					.getProperty("maxConnectionsSize"));
			this.isWaitingFreeConnection = Boolean.parseBoolean(properties
					.getProperty("isWaitingFreeConnection"));
			this.initPoolSize = Integer.parseInt(properties
					.getProperty("initPoolSize"));
			this.connectionUpdateInterval = Long.parseLong(properties
					.getProperty("connectionUpdateInterval")) * 1000 * 60;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getDriverName() {
		return driverName;
	}

	public String getConnectionURL() {
		return connectionURL;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public int getMaxConnectionsSize() {
		return maxConnectionsSize;
	}

	public int getInitPoolSize() {
		return initPoolSize;
	}

	public long getConnectionUpdateInterval() {
		return connectionUpdateInterval;
	}

	public boolean isWaitingFreeConnection() {
		return isWaitingFreeConnection;
	}

}
