package com.example.chi_daram.controller;

import com.example.chi_daram.dto.Categorylevel2DTO;
import com.example.chi_daram.service.Categorylevel2Service;
// import jakarta.persistence.EntityNotFoundException; // این Exception توسط GlobalExceptionHandler مدیریت می‌شود
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
@RequestMapping("/api/categorylevel2s")
@Tag(name = "Category Level 2 Management", description = "APIs for managing Category Level 2 entities")
public class Categorylevel2Controller {

    private final Categorylevel2Service service;

    public Categorylevel2Controller(Categorylevel2Service service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Category Level 2", description = "Adds a new Category Level 2 to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category Level 2 created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categorylevel2DTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Related Category Level 1 not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Category Level 2 already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Categorylevel2DTO> createCategorylevel2(
            @RequestBody(description = "Category Level 2 details to create", required = true,
                    content = @Content(schema = @Schema(implementation = Categorylevel2DTO.class)))
            @org.springframework.web.bind.annotation.RequestBody Categorylevel2DTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        Categorylevel2DTO createdDto = service.createCategorylevel2(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Category Level 2", description = "Updates the details of an existing Category Level 2. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category Level 2 updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categorylevel2DTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category Level 2 not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Category Level 2 already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Categorylevel2DTO> updateCategorylevel2(
            @Parameter(description = "ID of the Category Level 2 to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Category Level 2 details for update", required = true,
                    content = @Content(schema = @Schema(implementation = Categorylevel2DTO.class)))
            @org.springframework.web.bind.annotation.RequestBody Categorylevel2DTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        Categorylevel2DTO updatedDto = service.updateCategorylevel2(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Category Level 2", description = "Deletes a Category Level 2 by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category Level 2 deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category Level 2 not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteCategorylevel2(
            @Parameter(description = "ID of the Category Level 2 to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteCategorylevel2(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Category Level 2 by ID", description = "Retrieves a single Category Level 2 by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Category Level 2 by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categorylevel2DTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Category Level 2 not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Categorylevel2DTO> getCategorylevel2ById(
            @Parameter(description = "ID of the Category Level 2 to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        Categorylevel2DTO dto = service.getCategorylevel2ById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Category Level 2s", description = "Retrieves a paginated list of all Category Level 2s. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Category Level 2s",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<Categorylevel2DTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<Categorylevel2DTO>> getAllCategorylevel2s(Pageable pageable) {
        Page<Categorylevel2DTO> dtos = service.getAllCategorylevel2s(pageable);
        return ResponseEntity.ok(dtos);
    }
}