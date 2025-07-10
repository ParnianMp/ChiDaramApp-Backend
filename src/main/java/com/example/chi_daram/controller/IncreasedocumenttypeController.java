package com.example.chi_daram.controller;

import com.example.chi_daram.dto.IncreasedocumenttypeDTO;
import com.example.chi_daram.service.IncreasedocumenttypeService;
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
@RequestMapping("/api/increasedocumenttypes")
@Tag(name = "Increase Document Type Management", description = "APIs for managing Increase Document Type entities")
public class IncreasedocumenttypeController {

    private final IncreasedocumenttypeService service;

    public IncreasedocumenttypeController(IncreasedocumenttypeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Increase Document Type", description = "Adds a new Increase Document Type to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Increase Document Type created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumenttypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "409", description = "Increase Document Type already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumenttypeDTO> createIncreasedocumenttype(
            @RequestBody(description = "Increase Document Type details to create", required = true,
                    content = @Content(schema = @Schema(implementation = IncreasedocumenttypeDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody IncreasedocumenttypeDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumenttypeDTO createdDto = service.createIncreasedocumenttype(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Increase Document Type", description = "Updates the details of an existing Increase Document Type. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Increase Document Type updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumenttypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document Type not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumenttypeDTO> updateIncreasedocumenttype(
            @Parameter(description = "ID of the Increase Document Type to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Increase Document Type details for update", required = true,
                    content = @Content(schema = @Schema(implementation = IncreasedocumenttypeDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody IncreasedocumenttypeDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumenttypeDTO updatedDto = service.updateIncreasedocumenttype(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Increase Document Type", description = "Deletes an Increase Document Type by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Increase Document Type deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document Type not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteIncreasedocumenttype(
            @Parameter(description = "ID of the Increase Document Type to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteIncreasedocumenttype(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Increase Document Type by ID", description = "Retrieves a single Increase Document Type by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Increase Document Type by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumenttypeDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document Type not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumenttypeDTO> getIncreasedocumenttypeById(
            @Parameter(description = "ID of the Increase Document Type to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumenttypeDTO dto = service.getIncreasedocumenttypeById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Increase Document Types", description = "Retrieves a paginated list of all Increase Document Types. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Increase Document Types",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<IncreasedocumenttypeDTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<IncreasedocumenttypeDTO>> getAllIncreasedocumenttypes(Pageable pageable) {
        Page<IncreasedocumenttypeDTO> dtos = service.getAllIncreasedocumenttypes(pageable);
        return ResponseEntity.ok(dtos);
    }
}