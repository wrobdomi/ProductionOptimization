package com.engineering.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.engineering.entity" })
public class MyDatabaseConfig {
	 	
		private Properties getHibernateProperties() {
			Properties props = new Properties();
			props.put("hibernate.format_sql", "true");
			//prop.put("hibernate.show_sql", "true");
			props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
			return props;
		}
	
		
	   @Bean
	   public DataSource getDataSource() {
	      BasicDataSource dataSource = new BasicDataSource();
	      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	      dataSource.setUrl("jdbc:mysql://localhost:3306/production3?useSSL=false&amp;serverTimezone=UTC" );
	      dataSource.setUsername("springstudent");
	      dataSource.setPassword("springstudent");
	      return dataSource;
	   }
	
	   
	   @Bean
		public SessionFactory getSessionFactory() {
			LocalSessionFactoryBuilder sessionFactory = new LocalSessionFactoryBuilder(getDataSource());
			sessionFactory
					.scanPackages("com.engineering.entity")
					.addProperties(getHibernateProperties());

			return sessionFactory.buildSessionFactory();
	   }
	   
	   
	   @Bean
	   public HibernateTransactionManager getTransactionManager() {
	      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	      transactionManager.setSessionFactory(getSessionFactory());
	      return transactionManager;
	   }
	   
}
