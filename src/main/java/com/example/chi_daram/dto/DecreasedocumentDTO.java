package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Data Transfer Object for Decrease Document (outbound stock) information")
public class DecreasedocumentDTO {

    @Schema(description = "Unique identifier of the Decrease Document", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Document number cannot be empty")
    @Size(max = 50, message = "Document number cannot exceed 50 characters")
    @Schema(description = "Unique number of the decrease document", example = "DEC-2023-001", required = true)
    private String documentNumber;

    @NotNull(message = "Decrease Type ID cannot be null")
    @Schema(description = "ID of the Decrease Document Type", example = "1", required = true)
    private Long decreaseTypeId; // Foreign key to Decreasedocumenttype

    @NotNull(message = "User ID cannot be null")
    @Schema(description = "ID of the User who created this decrease document", example = "101", required = true)
    private Long userId; // Foreign key to User

    @NotNull(message = "Decrease date cannot be null")
    @PastOrPresent(message = "Decrease date cannot be in the future")
    @Schema(description = "Date when the stock decrease occurred (YYYY-MM-DD)", example = "2023-01-15", required = true)
    private LocalDate decreaseDate;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Schema(description = "Additional description or notes for the decrease document", example = "Items issued for internal consumption.", nullable = true)
    private String description;

    // Constructors
    public DecreasedocumentDTO() {
    }

    public DecreasedocumentDTO(Long id, String documentNumber, Long decreaseTypeId, Long userId, LocalDate decreaseDate, String description) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.decreaseTypeId = decreaseTypeId;
        this.userId = userId;
        this.decreaseDate = decreaseDate;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getDecreaseTypeId() {
        return decreaseTypeId;
    }

    public void setDecreaseTypeId(Long decreaseTypeId) {
        this.decreaseTypeId = decreaseTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDecreaseDate() {
        return decreaseDate;
    }

    public void setDecreaseDate(LocalDate decreaseDate) {
        this.decreaseDate = decreaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}