package com.vts.ems.conf;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	

	
	 
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
		 
		    
		    .and()
		    .csrf()
		    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login").maximumSessions(1)
		   
		    ;
		  
		  
		  
	}
	 

	
	
	
		public PasswordEncoder passwordencoder(){
	     return new BCryptPasswordEncoder();
	    }
		
		
		
		
		
}
