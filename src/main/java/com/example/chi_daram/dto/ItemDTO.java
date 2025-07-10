package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for Item information")
public class ItemDTO {

    @Schema(description = "Unique identifier of the Item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Item name cannot be empty")
    @Size(min = 2, max = 255, message = "Item name must be between 2 and 255 characters")
    @Schema(description = "Name of the item", example = "Laptop Pro X", required = true)
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Schema(description = "Detailed description of the item", example = "High performance laptop with 16GB RAM and 512GB SSD.", nullable = true)
    private String description;

    @Min(value = 0, message = "Quantity cannot be negative")
    @Schema(description = "Current quantity of the item in stock", example = "15", required = true)
    private Integer quantity; // Should typically be managed by stock movements, not directly set by item DTO for create/update. Consider making it read-only for updates.

    @Size(max = 50, message = "Color cannot exceed 50 characters")
    @Schema(description = "Color of the item", example = "Space Gray", nullable = true)
    private String color;

    @Size(max = 50, message = "Size cannot exceed 50 characters")
    @Schema(description = "Size of the item (e.g., S, M, L, XL, or dimensions)", example = "15-inch", nullable = true)
    private String size;

    @Size(max = 2048, message = "Image URL cannot exceed 2048 characters")
    @Schema(description = "URL to the item's image", example = "https://example.com/images/laptop_pro_x.jpg", nullable = true)
    private String image;

    @NotNull(message = "Category Level 2 ID cannot be null")
    @Schema(description = "ID of the Category Level 2 to which this item belongs", example = "101", required = true)
    private Long categorylevel2Id; // To represent the foreign key in DTO

    // Constructors
    public ItemDTO() {
    }

    public ItemDTO(Long id, String name, String description, Integer quantity, String color, String size, String image, Long categorylevel2Id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.image = image;
        this.categorylevel2Id = categorylevel2Id;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCategorylevel2Id() {
        return categorylevel2Id;
    }

    public void setCategorylevel2Id(Long categorylevel2Id) {
        this.categorylevel2Id = categorylevel2Id;
    }
}