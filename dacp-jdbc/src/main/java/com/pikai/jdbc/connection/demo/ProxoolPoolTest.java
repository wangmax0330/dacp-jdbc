package com.pikai.jdbc.connection.demo;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Enumeration;

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

public class ProxoolPoolTest {
	public static void main(String[] args) throws Exception {
		ProxoolPoolTest obj = new ProxoolPoolTest();
		obj.test2();
	}

	int beginIndex = 0;
	int endIndex = 10; // 取10条记录
	String MySQLdbTableName = "user";
	String MySQLreq = "select * from " + MySQLdbTableName + " limit " + beginIndex + ", " + endIndex;

	public void test2() throws Exception {

		System.out.println("------------");
		String filePath = this.getClass().getClassLoader().getResource("").getPath();
		System.out.println(filePath);

		// Java应用中先要加载配置文件，否则谁知道你配置给谁用的
		JAXPConfigurator.configure(filePath + "config/proxool.xml", false);
		// 1：注册驱动类，这次这个驱动已经不是Oracle的驱动了，是Proxool专用的驱动
		Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		// 2：创建数据库连接，这个参数是一个字符串，是数据源的别名，在配置文件中配置的timalias，参数格式为：proxool.数据源的别名
		Connection conn = DriverManager.getConnection("proxool.mysql");
		for (int i = 0; i < 20; i++)
			conn = DriverManager.getConnection("proxool.mysql");
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(MySQLreq);
		ResultSetMetaData rsmd = res.getMetaData();
		int columnCount = rsmd.getColumnCount();
		int rowCount = 0;
		while (res.next()) {
			System.out.print(rowCount + " ");
			rowCount++;
			for (int j = 1; j <= columnCount; j++) {
				String strRes = res.getString(j);
				System.out.print(strRes + "|\t");
			}
			System.out.println();
		}
		conn.close();
	}
}
