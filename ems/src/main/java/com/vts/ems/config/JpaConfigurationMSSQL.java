package com.vts.ems.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.vts.ems.attendance.model.MSSQL.*",  entityManagerFactoryRef = "EntityManagerMSSQL", transactionManagerRef = "oracletransactionManager" )
public class JpaConfigurationMSSQL
{
	
	@Autowired
	private Environment env;
	  
	    
	@Bean
	public DataSource oracleDataSource() {
	
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(env.getProperty("datasource2.datasource.driver-class-name"));
		dataSource.setJdbcUrl(env.getProperty("datasource2.datasource.url"));
		dataSource.setUsername(env.getProperty("datasource2.datasource.username"));
		dataSource.setPassword(env.getProperty("datasource2.datasource.password"));
		dataSource.setConnectionTimeout(30000);
		return dataSource;
	}

	@Bean(name="EntityManagerMSSQL")
	public LocalContainerEntityManagerFactoryBean oracleentityManagerFactory() throws NamingException 
	{
		 LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		 factoryBean.setDataSource(oracleDataSource());
		 factoryBean.setPackagesToScan(new String[] { "com.vts.ems.attendance.model.*" });
		 factoryBean.setJpaVendorAdapter(oraclejpaVendorAdapter());
		 factoryBean.setJpaProperties(oraclejpaProperties());
		 return factoryBean;
	 }
	 
	    /*
	     * Provider specific adapter.
	     */
	    @Bean
	    public JpaVendorAdapter oraclejpaVendorAdapter() {
	        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
	        return hibernateJpaVendorAdapter;
	    }
	
	  @Bean
	    public Properties oraclejpaProperties() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
	       // properties.put("hibernate.hbm2ddl.auto", "update");
	        properties.put("hibernate.show_sql", "true");
	        return properties;
	    }
	  
	  
	  @Bean(name="oracletransactionManager")
	  @Autowired
	    public PlatformTransactionManager oracletransactionManager(EntityManagerFactory emf) {
	        JpaTransactionManager txManager = new JpaTransactionManager();
	        txManager.setEntityManagerFactory(emf);
	        return txManager;
	    }
	  
}
