package com.example.chi_daram.controller;

import com.example.chi_daram.dto.DecreasedocumentDTO;
import com.example.chi_daram.service.DecreasedocumentService;
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
@RequestMapping("/api/decreasedocuments")
@Tag(name = "Decrease Document Management", description = "APIs for managing Decrease Document entities")
public class DecreasedocumentController {

    private final DecreasedocumentService service;

    public DecreasedocumentController(DecreasedocumentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Decrease Document", description = "Adds a new Decrease Document to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Decrease Document created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Related Decrease Document Type or User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumentDTO> createDecreasedocument(
            @RequestBody(description = "Decrease Document details to create", required = true,
                    content = @Content(schema = @Schema(implementation = DecreasedocumentDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody DecreasedocumentDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumentDTO createdDto = service.createDecreasedocument(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Decrease Document", description = "Updates the details of an existing Decrease Document. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Decrease Document updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumentDTO> updateDecreasedocument(
            @Parameter(description = "ID of the Decrease Document to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Decrease Document details for update", required = true,
                    content = @Content(schema = @Schema(implementation = DecreasedocumentDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody DecreasedocumentDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumentDTO updatedDto = service.updateDecreasedocument(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Decrease Document", description = "Deletes a Decrease Document by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Decrease Document deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteDecreasedocument(
            @Parameter(description = "ID of the Decrease Document to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteDecreasedocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Decrease Document by ID", description = "Retrieves a single Decrease Document by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Decrease Document by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumentDTO> getDecreasedocumentById(
            @Parameter(description = "ID of the Decrease Document to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumentDTO dto = service.getDecreasedocumentById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Decrease Documents", description = "Retrieves a paginated list of all Decrease Documents. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Decrease Documents",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<DecreasedocumentDTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<DecreasedocumentDTO>> getAllDecreasedocuments(Pageable pageable) {
        Page<DecreasedocumentDTO> dtos = service.getAllDecreasedocuments(pageable);
        return ResponseEntity.ok(dtos);
    }
}