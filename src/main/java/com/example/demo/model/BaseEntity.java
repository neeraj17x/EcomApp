package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

/*
 * To be used later
 * Get better knowledge about this
 * This class will be used by other model/entity classes to get the common fields and methods at the same place
 */

@MappedSuperclass // This will allow the fields to be inherited by subclasses
@Data
public class BaseEntity {

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
