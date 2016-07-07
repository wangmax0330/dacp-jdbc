package com.pikai.jdbc;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.pikai.jdbc.connection.SpringJdbcTemplate;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.setValidating(false);
		context.load("classpath*:config/serverContext-datasource.xml");
		context.refresh();
		SpringJdbcTemplate springJdbcTemplate = context.getBean(SpringJdbcTemplate.class);
		System.out.println(springJdbcTemplate.getJdbcTemplate());
		springJdbcTemplate.testPpreparedStatement1();
		springJdbcTemplate.testPreparedStatement2();
	}
}
