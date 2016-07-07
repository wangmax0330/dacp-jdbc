package com.pikai.jdbc.connection.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * A test project demonstrating the use of BoneCP in a JDBC environment.
 * 
 * @author wwadge
 *
 */
public class BonecpPoolTest {
	/**
	 * Start test
	 * 
	 * @param args
	 *            none expected.
	 */
	public static void main(String[] args) {
		BoneCP connectionPool = null;
		Connection connection = null;

		try {
			// load the database driver (make sure this is in your classpath!)
			// Class.forName("org.hsqldb.jdbcDriver");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {

			// setup the connection pool
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test");
			// config.setJdbcUrl("jdbc:hsqldb:mem:test"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
			config.setUsername("root");
			config.setPassword("123456");
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config); // setup the connection pool
			connection = connectionPool.getConnection(); // fetch a connection
			if (connection != null) {
				System.out.println("Connection successful!");
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT 1"); // do something with the connection.
				while (rs.next()) {
					System.out.println(rs.getString(1)); // should print out "1"'
				}
			}
			connectionPool.shutdown(); // shutdown connection pool.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
