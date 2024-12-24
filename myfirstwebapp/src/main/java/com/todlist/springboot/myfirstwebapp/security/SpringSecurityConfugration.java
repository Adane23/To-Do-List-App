package com.todlist.springboot.myfirstwebapp.security;
import static org.springframework.security.config.Customizer.withDefaults;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SpringSecurityConfugration {
	
	
	//InMemoryUserDetailsManager 
	//InMemoryUserDetailsManager(UserDetails... users)
	
	@Bean
	public InMemoryUserDetailsManager createUseDetailsManager() {
		
		UserDetails userDetails1 = createNewUser("Adane", "1234567890");
		UserDetails userDetails2 = createNewUser("Yordi", "7654321");
		
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		
		UserDetails userDetails = User.builder()
									.passwordEncoder(passwordEncoder )
									.username(username)
									.password(password)
									.roles("USER", "ADMIN")
									.build();
		return userDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Authorize all requests to be authenticated
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
		
		// Enable form-based login with default configuration
		http.formLogin(withDefaults());
		
		// Consider whether you really need to disable CSRF protection
		http.csrf().disable();
		
		// Disable frame options to allow the use of H2 console
		http.headers().frameOptions().disable();
		
		// Build and return the SecurityFilterChain
		return http.build();
	}
	
	
	
	
	
	
	
	
	
	
	

}
