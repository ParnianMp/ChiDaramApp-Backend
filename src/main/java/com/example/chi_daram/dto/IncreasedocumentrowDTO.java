package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Data Transfer Object for a row within an Increase Document")
public class IncreasedocumentrowDTO {

    @Schema(description = "Unique identifier of the Increase Document Row", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Increase Document ID cannot be null")
    @Schema(description = "ID of the parent Increase Document", example = "10", required = true)
    private Long increasedocumentId; // Foreign key to Increasedocument

    @NotNull(message = "Item ID cannot be null")
    @Schema(description = "ID of the Item being increased", example = "201", required = true)
    private Long itemId; // Foreign key to Item

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Quantity of the item increased", example = "10", required = true)
    private Integer quantity;

    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    @Schema(description = "Unit price of the item at the time of increase", example = "20.50", required = true)
    private BigDecimal unitPrice;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description for this specific item increase row", example = "Received in good condition", nullable = true)
    private String description;

    // Constructors
    public IncreasedocumentrowDTO() {
    }

    public IncreasedocumentrowDTO(Long id, Long increasedocumentId, Long itemId, Integer quantity, BigDecimal unitPrice, String description) {
        this.id = id;
        this.increasedocumentId = increasedocumentId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIncreasedocumentId() {
        return increasedocumentId;
    }

    public void setIncreasedocumentId(Long increasedocumentId) {
        this.increasedocumentId = increasedocumentId;
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