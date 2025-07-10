package com.example.chi_daram.entity;

import jakarta.persistence.*;
import java.time.LocalDate; // For 'date' type in PostgreSQL

@Entity
@Table(name = "increasedocument")
public class Increasedocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    private Long id;

    @Column(name = "documentnumber", nullable = false, length = 255, unique = true) // Document number should likely be unique
    private String documentNumber;

    @ManyToOne(fetch = FetchType.LAZY) // Many documents can have one type
    @JoinColumn(name = "increasetypeid", nullable = false) // Foreign key to Increasedocumenttype
    private Increasedocumenttype increaseType;

    @ManyToOne(fetch = FetchType.LAZY) // Many documents can be associated with one user
    @JoinColumn(name = "userid", nullable = false) // Foreign key to User entity (Assuming a User entity exists)
    private User user; // Placeholder for User entity. You'll need to define User entity and UserRepository.

    @Column(name = "increasedate", nullable = false)
    private LocalDate increaseDate;

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

    public Increasedocumenttype getIncreaseType() {
        return increaseType;
    }

    public void setIncreaseType(Increasedocumenttype increaseType) {
        this.increaseType = increaseType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getIncreaseDate() {
        return increaseDate;
    }

    public void setIncreaseDate(LocalDate increaseDate) {
        this.increaseDate = increaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}