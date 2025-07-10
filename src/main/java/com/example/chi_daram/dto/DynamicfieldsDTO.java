package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for Dynamic Fields associated with an Item")
public class DynamicfieldsDTO {

    @Schema(description = "Unique identifier of the Dynamic Field", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Description (field value) cannot be empty")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Schema(description = "Value or content of the dynamic field (e.g., 'Screen Size: 6.7 inches')", example = "Material: Leather", required = true)
    private String description; // Typo in original schema 'descripsion' reflected as 'description' here for consistency

    @NotNull(message = "Item ID cannot be null")
    @Schema(description = "ID of the Item to which this dynamic field belongs", example = "201", required = true)
    private Long itemId; // Foreign key to Item

    // Constructors
    public DynamicfieldsDTO() {
    }

    public DynamicfieldsDTO(Long id, String description, Long itemId) {
        this.id = id;
        this.description = description;
        this.itemId = itemId;
    }

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}