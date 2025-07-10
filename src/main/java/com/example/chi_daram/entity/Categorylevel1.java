package com.example.chi_daram.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorylevel1")
public class Categorylevel1 {

    @Id
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    // Constructors
    public Categorylevel1() {
    }

    public Categorylevel1(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}/*package com.example.chi_daram.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categorylevel1")
@Data
public class Categorylevel1 {
    @Id
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;
}
*/