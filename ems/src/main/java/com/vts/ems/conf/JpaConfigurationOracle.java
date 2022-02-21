package com.vts.ems.conf;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration

  @EnableJpaRepositories(basePackages = "com.vts.oracle*",
  entityManagerFactoryRef = "EntityManagerOracle", transactionManagerRef
 = "oracletransactionManager" )
 

public class JpaConfigurationOracle
{
	
	  @Autowired
	    private Environment env;
	 
	 
	 
	
	    @Bean
	    public DataSource oracleDataSource() {
	  
	        HikariDataSource dataSource
	          = new HikariDataSource();
	        dataSource.setDriverClassName(
	          env.getProperty("app.datasource.oracle.driver-class-name"));
	        dataSource.setJdbcUrl(env.getProperty("app.datasource.oracle.url"));
	        dataSource.setUsername(env.getProperty("app.datasource.oracle.username"));
	        dataSource.setPassword(env.getProperty("app.datasource.oracle.password"));
	 
	        return dataSource;
	    }
	 
	 
	 
	 
	 
	  @Lazy
	    @Bean(name="EntityManagerOracle")
	    public LocalContainerEntityManagerFactoryBean oracleentityManagerFactory() throws NamingException {
	     
		  
	    	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	        factoryBean.setDataSource(oracleDataSource());
	        factoryBean.setPackagesToScan(new String[] { "com.vts.oracle*" });
	        factoryBean.setJpaVendorAdapter(oraclejpaVendorAdapter());
	        factoryBean.setJpaProperties(oraclejpaProperties());
	        return factoryBean;
	    }
	 
	    /*
	     * Provider specific adapter.
	     */
	  @Lazy
	    @Bean
	    public JpaVendorAdapter oraclejpaVendorAdapter() {
	        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
	        return hibernateJpaVendorAdapter;
	    }
	 
	    /*
	     * Here you can specify any provider specific properties.
	     */
	  @Lazy
	  @Bean
	    public Properties oraclejpaProperties() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
	       // properties.put("hibernate.hbm2ddl.auto", "update");
	        properties.put("hibernate.show_sql", "true");
	        return properties;
	    }
	  
	  
	    @Bean
	    @Autowired
	    public PlatformTransactionManager oracletransactionManager(EntityManagerFactory emf) {
	        JpaTransactionManager txManager = new JpaTransactionManager();
	        txManager.setEntityManagerFactory(emf);
	        return txManager;
	    }
	  
}
