package com.example.chi_daram.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "itemstatus")
public class ItemStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    private Long id;

    @Column(name = "status", nullable = false, columnDefinition = "TEXT") // 'status' column is TEXT and NOT NULL
    private String status;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}