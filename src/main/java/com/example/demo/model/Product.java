package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String name;
	private String brand;
	@Column(nullable = false)
	private String category;
	private String description;
	private BigDecimal price;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date releaseDate;
	private Integer quantity;
	@Column(nullable = false, updatable = false)
	private LocalDateTime created;
	@Column(insertable = false)
	private LocalDateTime modified;
	
	// For Image
	private String imageName;
	private String imageType;
	@Lob
	private byte[] imageData;
	
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
