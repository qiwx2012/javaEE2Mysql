package com.qiwx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtils {
	String driver;
	String url;
	String username;
	String password;
	Connection connection;

	public static void main(String[] arg) {
		new DBUtils().getDbConfit();
	}

	public void getDbConfit() {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader()
					.getResourceAsStream("DBConfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver = prop.getProperty("driver");
		url = prop.getProperty("url");
		username = prop.getProperty("username");
		password = prop.getProperty("password");
		connection = getConnection(driver, url, username, password);
		// select();
		addData();
	}

	public Connection getConnection(String driver, String url, String username,
			String password) {
		try {
			// 加载MYSQL JDBC驱动程序
			Class.forName(driver);
			System.out.println("Success loading Mysql Driver!");
			// 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 添加数据
	private void addData() {
		try {
			//事务不自动提交，手动提交
			connection.setAutoCommit(false);
			String sql = "update student set age=age+10 where name='小明'";
			String sql1 = "update student set age=age-10 where name='小张'";
			Statement stmt = connection.createStatement();
			stmt.equals(sql);
			stmt.execute(sql1);
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				//遇到错误事务回滚
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			closeDb();
		}

	}

	// 查询
	public void select() {
		try {
			Statement stmt = connection.createStatement();
			// student 为你表的名称
			ResultSet rs = stmt.executeQuery("select * from student");
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDb();
		}
	}

	// 关闭数据库连接
	private void closeDb() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
