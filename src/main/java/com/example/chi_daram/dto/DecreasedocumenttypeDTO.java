package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for Decrease Document Type information")
public class DecreasedocumenttypeDTO {

    @Schema(description = "Unique identifier of the Decrease Document Type", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    @Schema(description = "Title or name of the decrease document type", example = "Sales", required = true)
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description of the decrease document type", example = "Decrease due to customer sales.", nullable = true)
    private String description;

    // Constructors
    public DecreasedocumenttypeDTO() {
    }

    public DecreasedocumenttypeDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}