package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;

	// Spring Security options
	// Option 1 >> with default user
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.csrf(customizer -> customizer.disable());	//disable CSRF
		//httpSecurity.authorizeHttpRequests(request -> request.anyRequest().authenticated()); 	// enable authorization for all requests
		httpSecurity.authorizeHttpRequests(request -> request
				.requestMatchers("users/register", "login", "users/login").permitAll()		// allow register and login request without authentication
				.anyRequest().authenticated()); 	// enable authorization for all requests
		httpSecurity.formLogin(Customizer.withDefaults());		// enable form login
		httpSecurity.httpBasic(Customizer.withDefaults());		// enable login through REST API
		httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
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
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());		// Plain Text
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	/*
	 * The @Bean annotation in Spring is used to define a bean within a Spring ApplicationContext.
	 * A bean in Spring is an object that is managed by the Spring container. 
	 * The @Bean annotation indicates that the method it is attached to will create and configure an object that Spring will manage, and
	 * the object will be available for dependency injection.
	 */
	
	// Set Default Password encoder for all encryption methods
	@Bean
	protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
