package com.pikai.jdbc.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

public class SpringJdbcTemplate {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	// 注入方法1
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void testPpreparedStatement1() {
		int count = jdbcTemplate.execute(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				return conn.prepareStatement("select count(*) from user");
			}
		}, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.execute();
				ResultSet rs = pstmt.getResultSet();
				rs.next();
				return rs.getInt(1);
			}
		});
		System.out.println(count);
		// Assert.assertEquals(1, count);
	}

	public void testPreparedStatement2() {
		String insertSql = "insert into user(username) values (?)";
		int count = jdbcTemplate.update(insertSql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setObject(1, "name4");
			}
		});
		Assert.assertEquals(1, count);
		String deleteSql = "delete from user where username=?";
		count = jdbcTemplate.update(deleteSql, new Object[] { "name4" });
		System.out.println(count);
		// Assert.assertEquals(1, count);
	}
}
