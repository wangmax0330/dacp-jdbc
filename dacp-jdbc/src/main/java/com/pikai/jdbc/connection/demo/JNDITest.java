package com.pikai.jdbc.connection.demo;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * JNDI 需要tomcat context.xml配置Resource,在这里,我们没有将JNDI整合到Spring里面去,使用的是独立的方式去获取jdbc source
 * 
 * @author Administrator
 *
 */
public class JNDITest {
	public static Connection getConnection() {
		try {
			InitialContext context = new InitialContext();
			DataSource dSource = (DataSource) context.lookup("java:comp/env/jdbc/test");
			Connection conn = dSource.getConnection();
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
