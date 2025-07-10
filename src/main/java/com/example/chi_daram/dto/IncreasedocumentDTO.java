package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Data Transfer Object for Increase Document (inbound stock) information")
public class IncreasedocumentDTO {

    @Schema(description = "Unique identifier of the Increase Document", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Document number cannot be empty")
    @Size(max = 50, message = "Document number cannot exceed 50 characters")
    @Schema(description = "Unique number of the increase document", example = "INC-2023-001", required = true)
    private String documentNumber;

    @NotNull(message = "Increase Type ID cannot be null")
    @Schema(description = "ID of the Increase Document Type", example = "1", required = true)
    private Long increaseTypeId; // Foreign key to Increasedocumenttype

    @NotNull(message = "User ID cannot be null")
    @Schema(description = "ID of the User who created this increase document", example = "101", required = true)
    private Long userId; // Foreign key to User

    @NotNull(message = "Increase date cannot be null")
    @PastOrPresent(message = "Increase date cannot be in the future")
    @Schema(description = "Date when the stock increase occurred (YYYY-MM-DD)", example = "2023-01-10", required = true)
    private LocalDate increaseDate;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Schema(description = "Additional description or notes for the increase document", example = "Items received from supplier XYZ.", nullable = true)
    private String description;

    // Constructors
    public IncreasedocumentDTO() {
    }

    public IncreasedocumentDTO(Long id, String documentNumber, Long increaseTypeId, Long userId, LocalDate increaseDate, String description) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.increaseTypeId = increaseTypeId;
        this.userId = userId;
        this.increaseDate = increaseDate;
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

    public Long getIncreaseTypeId() {
        return increaseTypeId;
    }

    public void setIncreaseTypeId(Long increaseTypeId) {
        this.increaseTypeId = increaseTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getIncreaseDate() {
        return increaseDate;
    }

    public void setIncreaseDate(LocalDate increaseDate) {
        this.increaseDate = increaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}