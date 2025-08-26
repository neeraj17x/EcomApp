package com.example.demo.loader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;

@Component
public class DataLoader implements CommandLineRunner {

	private final UserRepo userRepo;
	//private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	private final PasswordEncoder passwordEncoder;
	
	// This is called Constructor Injection
	public DataLoader(UserRepo userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void run(String... args) throws Exception {
		// Check if the user already exists
		if(userRepo.findByUsername("admin") == null) {
			// Create an admin user with encrypted password
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            // adminUser.setRole("ADMIN");
            adminUser.setEnabled(true);
            adminUser.setIsAccountExpired(false);
            adminUser.setIsAccountLocked(false);
            adminUser.setIsCredentialsExpired(false);

            // Save the admin user to the database >>
            userRepo.save(adminUser);
            System.out.println("Admin user created");
		}
	}

}
