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
			// ����MYSQL JDBC��������
			Class.forName(driver);
			System.out.println("Success loading Mysql Driver!");
			// ����URLΪ jdbc:mysql//��������ַ/���ݿ��� �������2�������ֱ��ǵ�½�û���������
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �������
	private void addData() {
		try {
			//�����Զ��ύ���ֶ��ύ
			connection.setAutoCommit(false);
			String sql = "update student set age=age+10 where name='С��'";
			String sql1 = "update student set age=age-10 where name='С��'";
			Statement stmt = connection.createStatement();
			stmt.equals(sql);
			stmt.execute(sql1);
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				//������������ع�
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			closeDb();
		}

	}

	// ��ѯ
	public void select() {
		try {
			Statement stmt = connection.createStatement();
			// student Ϊ��������
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

	// �ر����ݿ�����
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
