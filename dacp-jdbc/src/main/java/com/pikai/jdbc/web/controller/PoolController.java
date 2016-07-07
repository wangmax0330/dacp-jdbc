/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.pikai.jdbc.web.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikai.jdbc.connection.SpringJdbcTemplate;
import com.pikai.jdbc.connection.demo.JNDITest;

@Controller
public class PoolController {
	@Resource
	SpringJdbcTemplate springJdbcTemplate;

	@RequestMapping(value = "/jndi/testConn", method = { RequestMethod.GET })
	@ResponseBody
	public String testConn(HttpServletRequest request, HttpServletResponse response) {
		try {
			int beginIndex = 0;
			int endIndex = 10; // 取10条记录
			String MySQLdbTableName = "user";
			final String MySQLreq = "select * from " + MySQLdbTableName + " limit " + beginIndex + ", " + endIndex;
			/*-------------------------------------------------------*/
			// 通过配置Spring JdbcTemplate 配置数据源
			JdbcTemplate jdbcTemplate = springJdbcTemplate.getJdbcTemplate();
			/*-------------------------------------------------------*/
			// List<User> list = (List<User>) jdbcTemplate.queryForList(MySQLreq, null, User.class);
			jdbcTemplate.execute(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					return conn.prepareStatement(MySQLreq);
				}
			}, new PreparedStatementCallback<Integer>() {
				@Override
				public Integer doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
					pstmt.execute();
					ResultSet rs = pstmt.getResultSet();
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();
					int rowCount = 0;
					while (rs.next()) {
						System.out.print(rowCount + " ");
						rowCount++;
						for (int j = 1; j <= columnCount; j++) {
							String strRes = rs.getString(j);
							System.out.print(strRes + "|\t");
						}
						System.out.println();
					}
					return 1;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping(value = "/jndi/testConn2", method = { RequestMethod.GET })
	@ResponseBody
	public String testConn2(HttpServletRequest request, HttpServletResponse response) {
		try {
			int beginIndex = 0;
			int endIndex = 10; // 取10条记录
			String MySQLdbTableName = "user";
			String MySQLreq = "select * from " + MySQLdbTableName + " limit " + beginIndex + ", " + endIndex;
			/*-------------------------------------------------------*/
			// DataSource 的方式查找数据源
			Connection conn = JNDITest.getConnection();
			/*-------------------------------------------------------*/
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

}
