package com.example.chi_daram.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorylevel2")
public class Categorylevel2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many Categorylevel2 can belong to one Categorylevel1
    @JoinColumn(name = "categorylevel1id", nullable = false) // Foreign key column
    private Categorylevel1 categorylevel1; // Reference to Categorylevel1 entity

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categorylevel1 getCategorylevel1() {
        return categorylevel1;
    }

    public void setCategorylevel1(Categorylevel1 categorylevel1) {
        this.categorylevel1 = categorylevel1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}