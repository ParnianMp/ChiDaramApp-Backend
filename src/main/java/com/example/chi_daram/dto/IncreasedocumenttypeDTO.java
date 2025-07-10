package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for Increase Document Type information")
public class IncreasedocumenttypeDTO {

    @Schema(description = "Unique identifier of the Increase Document Type", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    @Schema(description = "Title or name of the increase document type", example = "Purchase", required = true)
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description of the increase document type", example = "Increase due to new purchases from suppliers.", nullable = true)
    private String description;

    // Constructors
    public IncreasedocumenttypeDTO() {
    }

    public IncreasedocumenttypeDTO(Long id, String title, String description) {
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