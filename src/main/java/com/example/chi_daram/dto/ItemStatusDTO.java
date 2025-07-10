package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for Item Status information")
public class ItemStatusDTO {

    @Schema(description = "Unique identifier of the Item Status", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Status cannot be empty")
    @Size(min = 2, max = 50, message = "Status must be between 2 and 50 characters")
    @Schema(description = "The status of an item (e.g., 'Available', 'Damaged', 'Reserved')", example = "Available", required = true)
    private String status;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description of the item status", example = "Item is ready for use or sale.", nullable = true)
    private String description;

    // Constructors
    public ItemStatusDTO() {
    }

    public ItemStatusDTO(Long id, String status, String description) {
        this.id = id;
        this.status = status;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}