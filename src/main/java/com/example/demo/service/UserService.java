package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;

@Service
public class UserService {
	//@Autowired
	private UserRepo userRepo;
	//private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);	// Not Recommended for Flexibility
	private final PasswordEncoder encoder;
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public UserService(UserRepo userRepo, PasswordEncoder encoder, JWTService jwtService) {
		//super();
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.jwtService = jwtService;
	}

	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public String verify(User user) {
		Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			//return "You are now logged in!";
			return jwtService.generateToken(user.getUsername());
		}
		return null;
	}

}
