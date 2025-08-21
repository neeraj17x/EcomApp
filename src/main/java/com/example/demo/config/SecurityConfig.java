package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	// Spring Security options
	// Option 1 >> with default user
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.csrf(customizer -> customizer.disable());	//disable CSRF
		httpSecurity.authorizeHttpRequests(request -> request.anyRequest().authenticated()); 	// enable authorization for all requests
		httpSecurity.formLogin(Customizer.withDefaults());		// enable form login
		httpSecurity.httpBasic(Customizer.withDefaults());		// enable login through REST API
		httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return httpSecurity.build();
	}
	
	// Option 2 >> In Memory User Details >> to create hard coded users
	@Bean
	protected UserDetailsService userDetailsService() {
		@SuppressWarnings("deprecation")
		UserDetails user1 = User.withDefaultPasswordEncoder().username("anjan").password("anjan").roles("admin").build();
		@SuppressWarnings("deprecation")
		UserDetails user2 = User.withDefaultPasswordEncoder().username("sonu").password("sonu").roles("Super").build();
		return new InMemoryUserDetailsManager(user1, user2);
	}
	
	// Option 3 >> User AUthentications from database >> Set up Authentication Provider
	@SuppressWarnings("deprecation")
	@Bean
	protected AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}
