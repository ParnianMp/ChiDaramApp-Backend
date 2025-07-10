package com.example.chi_daram.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dynamicfields")
public class Dynamicfields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    private Long id;

    @Column(name = "descripsion", columnDefinition = "TEXT") // Typo in original schema 'descripsion' retained
    private String description;

    @ManyToOne(fetch = FetchType.LAZY) // Many dynamic fields can belong to one item
    @JoinColumn(name = "itemid", nullable = false) // Foreign key to Item
    private Item item;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}