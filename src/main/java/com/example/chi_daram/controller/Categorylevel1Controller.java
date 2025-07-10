package com.example.chi_daram.controller;

import com.example.chi_daram.dto.Categorylevel1DTO;
import com.example.chi_daram.service.Categorylevel1Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// OpenAPI Imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/categorylevel1s")
@Tag(name = "Category Level 1 Management", description = "APIs for managing Category Level 1 entities")
public class Categorylevel1Controller {

    private final Categorylevel1Service service;

    public Categorylevel1Controller(Categorylevel1Service service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Category Level 1", description = "Adds a new Category Level 1 to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category Level 1 created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categorylevel1DTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "409", description = "Category Level 1 already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Categorylevel1DTO> create(
            @RequestBody(description = "Category Level 1 details to create", required = true,
                    content = @Content(schema = @Schema(implementation = Categorylevel1DTO.class)))
            @org.springframework.web.bind.annotation.RequestBody Categorylevel1DTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        Categorylevel1DTO createdDto = service.createCategorylevel1(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Category Level 1", description = "Updates the details of an existing Category Level 1. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category Level 1 updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categorylevel1DTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category Level 1 not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Category Level 1 already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Categorylevel1DTO> update(
            @Parameter(description = "ID of the Category Level 1 to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Category Level 1 details for update", required = true,
                    content = @Content(schema = @Schema(implementation = Categorylevel1DTO.class)))
            @org.springframework.web.bind.annotation.RequestBody Categorylevel1DTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        Categorylevel1DTO updatedDto = service.updateCategorylevel1(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Category Level 1", description = "Deletes a Category Level 1 by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category Level 1 deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category Level 1 not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the Category Level 1 to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteCategorylevel1(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Category Level 1 by ID", description = "Retrieves a single Category Level 1 by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Category Level 1 by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categorylevel1DTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category Level 1 not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Categorylevel1DTO> getById(
            @Parameter(description = "ID of the Category Level 1 to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        Categorylevel1DTO dto = service.getCategorylevel1ById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Category Level 1s", description = "Retrieves a paginated list of all Category Level 1s. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Category Level 1s",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<Categorylevel1DTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<Categorylevel1DTO>> getAll(Pageable pageable) {
        Page<Categorylevel1DTO> page = service.getAllCategorylevel1s(pageable);
        return ResponseEntity.ok(page);
    }
}