package com.vts.ems.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//@EnableScheduling
@ComponentScan(basePackages = "com.vts.*")
public class WebMvcConfigruation {

	 @Bean
	    public BCryptPasswordEncoder passwordencoder(){
	     return new BCryptPasswordEncoder();
	    }
	 
	 
}
