package com.example.chi_daram.controller;

import com.example.chi_daram.dto.DecreasedocumenttypeDTO;
import com.example.chi_daram.service.DecreasedocumenttypeService;
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
@RequestMapping("/api/decreasedocumenttypes")
@Tag(name = "Decrease Document Type Management", description = "APIs for managing Decrease Document Type entities")
public class DecreasedocumenttypeController {

    private final DecreasedocumenttypeService service;

    public DecreasedocumenttypeController(DecreasedocumenttypeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Decrease Document Type", description = "Adds a new Decrease Document Type to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Decrease Document Type created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumenttypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "409", description = "Decrease Document Type already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumenttypeDTO> createDecreasedocumenttype(
            @RequestBody(description = "Decrease Document Type details to create", required = true,
                    content = @Content(schema = @Schema(implementation = DecreasedocumenttypeDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody DecreasedocumenttypeDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumenttypeDTO createdDto = service.createDecreasedocumenttype(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Decrease Document Type", description = "Updates the details of an existing Decrease Document Type. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Decrease Document Type updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumenttypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document Type not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Decrease Document Type already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumenttypeDTO> updateDecreasedocumenttype(
            @Parameter(description = "ID of the Decrease Document Type to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Decrease Document Type details for update", required = true,
                    content = @Content(schema = @Schema(implementation = DecreasedocumenttypeDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody DecreasedocumenttypeDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumenttypeDTO updatedDto = service.updateDecreasedocumenttype(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Decrease Document Type", description = "Deletes a Decrease Document Type by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Decrease Document Type deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document Type not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteDecreasedocumenttype(
            @Parameter(description = "ID of the Decrease Document Type to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteDecreasedocumenttype(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Decrease Document Type by ID", description = "Retrieves a single Decrease Document Type by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Decrease Document Type by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DecreasedocumenttypeDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Decrease Document Type not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DecreasedocumenttypeDTO> getDecreasedocumenttypeById(
            @Parameter(description = "ID of the Decrease Document Type to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DecreasedocumenttypeDTO dto = service.getDecreasedocumenttypeById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Decrease Document Types", description = "Retrieves a paginated list of all Decrease Document Types. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Decrease Document Types",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<DecreasedocumenttypeDTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<DecreasedocumenttypeDTO>> getAllDecreasedocumenttypes(Pageable pageable) {
        Page<DecreasedocumenttypeDTO> dtos = service.getAllDecreasedocumenttypes(pageable);
        return ResponseEntity.ok(dtos);
    }
}