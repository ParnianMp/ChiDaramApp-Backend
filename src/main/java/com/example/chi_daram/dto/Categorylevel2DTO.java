package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for Category Level 2 information")
public class Categorylevel2DTO {

    @Schema(description = "Unique identifier of the Category Level 2", example = "101", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Category Level 1 ID cannot be null")
    @Schema(description = "ID of the parent Category Level 1 to which this Category Level 2 belongs", example = "1", required = true)
    private Long categorylevel1Id; // To represent the foreign key in DTO

    @NotBlank(message = "Category Level 2 name cannot be empty")
    @Size(min = 2, max = 100, message = "Category Level 2 name must be between 2 and 100 characters")
    @Schema(description = "Name of the Category Level 2", example = "Smartphones", required = true)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description of the Category Level 2", example = "Specific categories like smartphones, laptops under electronics.", nullable = true)
    private String description;

    // Constructors
    public Categorylevel2DTO() {
    }

    public Categorylevel2DTO(Long id, Long categorylevel1Id, String name, String description) {
        this.id = id;
        this.categorylevel1Id = categorylevel1Id;
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

    public Long getCategorylevel1Id() {
        return categorylevel1Id;
    }

    public void setCategorylevel1Id(Long categorylevel1Id) {
        this.categorylevel1Id = categorylevel1Id;
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