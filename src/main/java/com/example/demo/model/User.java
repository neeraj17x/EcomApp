package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;		// Primary Key
	private String username;
	private String password;
	private Boolean isAccountExpired;
	private Boolean isAccountLocked;
	private Boolean isCredentialsExpired;
	private Boolean enabled;
	@Column(nullable = false, updatable = false)
	private LocalDateTime created;
	@Column(insertable = false)
	private LocalDateTime modified;
	
	@PrePersist
    public void prePersist() {
        if (created == null) {
        	// Set current date and time before persist
            created = LocalDateTime.now();
        }
        if (modified == null) {
        	// Set current date and time before persist
        	modified = LocalDateTime.now();
        }
    }
	@PreUpdate
    public void preUpdate() {
		// Set last modified date before update
        modified = LocalDateTime.now();
    }
}
