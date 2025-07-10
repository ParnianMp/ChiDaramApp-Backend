package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for Category Level 1 information")
public class Categorylevel1DTO {

    @Schema(description = "Unique identifier of the Category Level 1", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Category Level 1 name cannot be empty")
    @Size(min = 2, max = 100, message = "Category Level 1 name must be between 2 and 100 characters")
    @Schema(description = "Name of the Category Level 1", example = "Electronics", required = true)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description of the Category Level 1", example = "Broad categories like electronics, clothing, etc.", nullable = true)
    private String description;

    // Constructors
    public Categorylevel1DTO() {
    }

    public Categorylevel1DTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

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
}