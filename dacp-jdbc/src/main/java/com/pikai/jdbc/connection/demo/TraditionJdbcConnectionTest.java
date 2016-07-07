package com.pikai.jdbc.connection.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TraditionJdbcConnectionTest {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String url = null;
		String user = null;
		String password = null;
		String sql = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载mysq驱动
		} catch (ClassNotFoundException e) {
			System.out.println("驱动加载错误");
			e.printStackTrace();// 打印出错详细信息
		}
		try {
			url = "jdbc:mysql://127.0.0.1/test?user=root&password=123456&useUnicode=true&&characterEncoding=utf-8&autoReconnect = true";// 简单写法：url
			user = "root";
			password = "123456";
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("数据库链接错误");
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			sql = "select * from user";// dept这张表有deptno，deptname和age这三个字段
			rs = stmt.executeQuery(sql);// 执行sql语句
			while (rs.next()) {
				System.out.print(rs.getString("USERNAME") + "   ");
				System.out.print(rs.getString("PASSWORD") + "   ");
				System.out.println(rs.getTimestamp("CREATE_TIME") + "   ");
			}
		} catch (SQLException e) {
			System.out.println("数据操作错误");
			e.printStackTrace();
		}
		// 关闭数据库
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			System.out.println("数据库关闭错误");
			e.printStackTrace();
		}
	}
}
