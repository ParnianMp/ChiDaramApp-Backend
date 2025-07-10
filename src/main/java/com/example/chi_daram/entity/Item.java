package com.example.chi_daram.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "size") // VARCHAR type in DB, Java String is fine
    private String size;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image; // Storing image path or URL

    @ManyToOne(fetch = FetchType.LAZY) // Many Items can belong to one Categorylevel2
    @JoinColumn(name = "categorylevel2id", nullable = false) // Foreign key column
    private Categorylevel2 categorylevel2; // Reference to Categorylevel2 entity

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Categorylevel2 getCategorylevel2() {
        return categorylevel2;
    }

    public void setCategorylevel2(Categorylevel2 categorylevel2) {
        this.categorylevel2 = categorylevel2;
    }
}