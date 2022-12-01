package com.datawise.satisactual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SatisactualApplication { // extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SatisactualApplication.class, args);
	}

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SatisactualApplication.class);
	}*/
}
