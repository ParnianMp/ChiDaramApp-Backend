package com.example.chi_daram.entity;

import jakarta.persistence.*;
import java.math.BigDecimal; // For 'numeric' type in PostgreSQL

@Entity
@Table(name = "increasedocumentrow")
public class Increasedocumentrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many rows belong to one increase document
    @JoinColumn(name = "increasedocumentid", nullable = false) // Foreign key to Increasedocument
    private Increasedocument increasedocument; // Correctly referencing Increasedocument entity

    @ManyToOne(fetch = FetchType.LAZY) // Many rows can reference one item
    @JoinColumn(name = "itemid", nullable = false) // Foreign key to Item
    private Item item; // Correctly referencing Item entity

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unitprice", precision = 19, scale = 2) // Example precision/scale for numeric
    private BigDecimal unitPrice;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Increasedocument getIncreasedocument() {
        return increasedocument;
    }

    public void setIncreasedocument(Increasedocument increasedocument) {
        this.increasedocument = increasedocument;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}