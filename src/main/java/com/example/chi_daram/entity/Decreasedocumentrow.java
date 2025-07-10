package com.example.chi_daram.entity;

import jakarta.persistence.*;
import java.math.BigDecimal; // For 'numeric' type in PostgreSQL

@Entity
@Table(name = "decreasedocumentrow")
public class Decreasedocumentrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many rows belong to one decrease document
    @JoinColumn(name = "decreasedocumentid", nullable = false) // Foreign key to Decreasedocument
    private Decreasedocument decreasedocument;

    @ManyToOne(fetch = FetchType.LAZY) // Many rows can reference one item
    @JoinColumn(name = "itemid", nullable = false) // Foreign key to Item
    private Item item;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "unitprice", precision = 19, scale = 2) // Example precision/scale for numeric
    private BigDecimal unitPrice;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Decreasedocument getDecreasedocument() {
        return decreasedocument;
    }

    public void setDecreasedocument(Decreasedocument decreasedocument) {
        this.decreasedocument = decreasedocument;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}