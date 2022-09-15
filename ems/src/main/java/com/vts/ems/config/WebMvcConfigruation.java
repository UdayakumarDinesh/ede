package com.vts.ems.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan(basePackages = "com.vts.*")
@ConfigurationProperties
@PropertySource(value= { "file:///${app.properties.path}", "file:///${NewsTele.properties.path}"})

public class WebMvcConfigruation {

	@Bean
	public BCryptPasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

}
