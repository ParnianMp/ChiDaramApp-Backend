package com.example.chi_daram.controller;

import com.example.chi_daram.dto.DecreasedocumentrowDTO;
import com.example.chi_daram.service.DecreasedocumentrowService;
//import com.example.chi_daram.exception.ResourceNotFoundException; // برای استفاده در ApiResponses
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
@RequestMapping("/api/decreasedocumentrows")
@Tag(name = "Decrease Document Row Management", description = "APIs for managing Decrease Document Row entities")
public class DecreasedocumentrowController {

    private final DecreasedocumentrowService service;

    public DecreasedocumentrowController(DecreasedocumentrowService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Decrease Document Row", description = "Adds a new Decrease Document Row to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Decrease Document Row created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumentrowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Related Decrease Document or Item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumentrowDTO> createDecreasedocumentrow(
            @RequestBody(description = "Decrease Document Row details to create", required = true,
                    content = @Content(schema = @Schema(implementation = DecreasedocumentrowDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody DecreasedocumentrowDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumentrowDTO createdDto = service.createDecreasedocumentrow(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Decrease Document Row", description = "Updates the details of an existing Decrease Document Row. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Decrease Document Row updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumentrowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document Row not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumentrowDTO> updateDecreasedocumentrow(
            @Parameter(description = "ID of the Decrease Document Row to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Decrease Document Row details for update", required = true,
                    content = @Content(schema = @Schema(implementation = DecreasedocumentrowDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody DecreasedocumentrowDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumentrowDTO updatedDto = service.updateDecreasedocumentrow(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Decrease Document Row", description = "Deletes a Decrease Document Row by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Decrease Document Row deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document Row not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteDecreasedocumentrow(
            @Parameter(description = "ID of the Decrease Document Row to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteDecreasedocumentrow(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Decrease Document Row by ID", description = "Retrieves a single Decrease Document Row by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Decrease Document Row by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumentrowDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document Row not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumentrowDTO> getDecreasedocumentrowById(
            @Parameter(description = "ID of the Decrease Document Row to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumentrowDTO dto = service.getDecreasedocumentrowById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Decrease Document Rows", description = "Retrieves a paginated list of all Decrease Document Rows. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Decrease Document Rows",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<DecreasedocumentrowDTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<DecreasedocumentrowDTO>> getAllDecreasedocumentrows(Pageable pageable) {
        Page<DecreasedocumentrowDTO> dtos = service.getAllDecreasedocumentrows(pageable);
        return ResponseEntity.ok(dtos);
    }
}