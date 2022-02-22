package com.vts.ems.conf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.vts.ems.login.CustomLogoutHandler;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	
	 @Autowired
	 private UserDetailsService userDetailsService;


	 
	 @Override
	 @Bean
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	     return super.authenticationManagerBean();
	 }
	 
	 @Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			 auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
		}
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  http.authorizeRequests()

		
		  .antMatchers("/").permitAll()
		  .antMatchers("/webjars/**").permitAll()
		  .antMatchers("/resources/**").permitAll()
		  .antMatchers("/view/**").permitAll()	 
		  .and()
		    .formLogin().loginPage("/login").defaultSuccessUrl("/welcome").failureUrl("/login?error")
		    .usernameParameter("username").passwordParameter("password")
		   
		   .and()
		   .exceptionHandling().accessDeniedPage("/accessdenied")
		 
		    
//		    .and()
//		    .csrf()
		    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login").maximumSessions(1)
		   
		    ;
		  
		  
		  
	}
	 

	
	
	
		public PasswordEncoder passwordencoder(){
	     return new BCryptPasswordEncoder();
	    }
		
		

		@Bean
		public LogoutHandler  logoutSuccessHandler() {
		return  new CustomLogoutHandler();
		}
		
		
		
}
