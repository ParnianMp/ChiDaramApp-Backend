package com.example.chi_daram.entity;

import jakarta.persistence.*;
import java.time.LocalDate; // For 'date' type in PostgreSQL

@Entity
@Table(name = "decreasedocument")
public class Decreasedocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    private Long id;

    @Column(name = "documentnumber", nullable = false, length = 255, unique = true) // Document number should be unique
    private String documentNumber;

    @ManyToOne(fetch = FetchType.LAZY) // Many documents can have one type
    @JoinColumn(name = "decreasetypeid", nullable = false) // Foreign key to Decreasedocumenttype
    private Decreasedocumenttype decreaseType;

    @ManyToOne(fetch = FetchType.LAZY) // Many documents can be associated with one user
    @JoinColumn(name = "userid", nullable = false) // Foreign key to User entity
    private User user;

    @Column(name = "decreasedate", nullable = false)
    private LocalDate decreaseDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Decreasedocumenttype getDecreaseType() {
        return decreaseType;
    }

    public void setDecreaseType(Decreasedocumenttype decreaseType) {
        this.decreaseType = decreaseType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDecreaseDate() {
        return decreaseDate;
    }

    public void setDecreaseDate(LocalDate decreaseDate) {
        this.decreaseDate = decreaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}