package com.example.chi_daram.controller;

import com.example.chi_daram.dto.IncreasedocumentrowDTO;
import com.example.chi_daram.service.IncreasedocumentrowService;
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
@RequestMapping("/api/increasedocumentrows")
@Tag(name = "Increase Document Row Management", description = "APIs for managing Increase Document Row entities")
public class IncreasedocumentrowController {

    private final IncreasedocumentrowService service;

    public IncreasedocumentrowController(IncreasedocumentrowService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Increase Document Row", description = "Adds a new Increase Document Row to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Increase Document Row created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumentrowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Related Increase Document or Item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Increase Document Row already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumentrowDTO> createIncreasedocumentrow(
            @RequestBody(description = "Increase Document Row details to create", required = true,
                    content = @Content(schema = @Schema(implementation = IncreasedocumentrowDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody IncreasedocumentrowDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumentrowDTO createdDto = service.createIncreasedocumentrow(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Increase Document Row", description = "Updates the details of an existing Increase Document Row. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Increase Document Row updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumentrowDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document Row not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumentrowDTO> updateIncreasedocumentrow(
            @Parameter(description = "ID of the Increase Document Row to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Increase Document Row details for update", required = true,
                    content = @Content(schema = @Schema(implementation = IncreasedocumentrowDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody IncreasedocumentrowDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumentrowDTO updatedDto = service.updateIncreasedocumentrow(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Increase Document Row", description = "Deletes an Increase Document Row by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Increase Document Row deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document Row not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteIncreasedocumentrow(
            @Parameter(description = "ID of the Increase Document Row to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteIncreasedocumentrow(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Increase Document Row by ID", description = "Retrieves a single Increase Document Row by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Increase Document Row by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumentrowDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document Row not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumentrowDTO> getIncreasedocumentrowById(
            @Parameter(description = "ID of the Increase Document Row to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumentrowDTO dto = service.getIncreasedocumentrowById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Increase Document Rows", description = "Retrieves a paginated list of all Increase Document Rows. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Increase Document Rows",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<IncreasedocumentrowDTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<IncreasedocumentrowDTO>> getAllIncreasedocumentrows(Pageable pageable) {
        Page<IncreasedocumentrowDTO> dtos = service.getAllIncreasedocumentrows(pageable);
        return ResponseEntity.ok(dtos);
    }
}