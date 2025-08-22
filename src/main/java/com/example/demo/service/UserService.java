package com.example.demo.service;

import java.util.List;

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
	
	public UserService(UserRepo userRepo, PasswordEncoder encoder) {
		//super();
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

}
