package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Data Transfer Object for a row within a Decrease Document")
public class DecreasedocumentrowDTO {

    @Schema(description = "Unique identifier of the Decrease Document Row", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Decrease Document ID cannot be null")
    @Schema(description = "ID of the parent Decrease Document", example = "10", required = true)
    private Long decreasedocumentId; // Foreign key to Decreasedocument

    @NotNull(message = "Item ID cannot be null")
    @Schema(description = "ID of the Item being decreased", example = "201", required = true)
    private Long itemId; // Foreign key to Item

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Quantity of the item decreased", example = "5", required = true)
    private Integer quantity;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description for this specific item decrease row", example = "Damaged units", nullable = true)
    private String description;

    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    @Schema(description = "Unit price of the item at the time of decrease", example = "19.99", required = true)
    private BigDecimal unitPrice;

    // Constructors
    public DecreasedocumentrowDTO() {
    }

    public DecreasedocumentrowDTO(Long id, Long decreasedocumentId, Long itemId, Integer quantity, String description, BigDecimal unitPrice) {
        this.id = id;
        this.decreasedocumentId = decreasedocumentId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDecreasedocumentId() {
        return decreasedocumentId;
    }

    public void setDecreasedocumentId(Long decreasedocumentId) {
        this.decreasedocumentId = decreasedocumentId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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