package com.vts.ems.config;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MsAccessDBConnectionConfig {
	
	@Autowired
	private Environment env;
	
//	@Bean(name= "MSAccessConnection")
	public Connection getMSAccessConnection() throws Exception {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//		Connection conn=(Connection) DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Admin/Desktop/SalariesJan2020.mdb;openExclusive=false:ignoreCase=true");
		Connection conn=(Connection) DriverManager.getConnection(env.getProperty("msaccess.datasource.url"),env.getProperty("msaccess.datasource.username"),env.getProperty("msaccess.datasource.password")) ;
		
		return conn;		
	}

}
