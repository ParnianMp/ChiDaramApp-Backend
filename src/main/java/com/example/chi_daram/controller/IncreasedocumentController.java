package com.example.chi_daram.controller;

import com.example.chi_daram.dto.IncreasedocumentDTO;
import com.example.chi_daram.service.IncreasedocumentService;
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
@RequestMapping("/api/increasedocuments")
@Tag(name = "Increase Document Management", description = "APIs for managing Increase Document entities")
public class IncreasedocumentController {

    private final IncreasedocumentService service;

    public IncreasedocumentController(IncreasedocumentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Increase Document", description = "Adds a new Increase Document to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Increase Document created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Related Increase Document Type or User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Increase Document already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumentDTO> createIncreasedocument(
            @RequestBody(description = "Increase Document details to create", required = true,
                    content = @Content(schema = @Schema(implementation = IncreasedocumentDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody IncreasedocumentDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumentDTO createdDto = service.createIncreasedocument(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Increase Document", description = "Updates the details of an existing Increase Document. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Increase Document updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumentDTO> updateIncreasedocument(
            @Parameter(description = "ID of the Increase Document to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Increase Document details for update", required = true,
                    content = @Content(schema = @Schema(implementation = IncreasedocumentDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody IncreasedocumentDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumentDTO updatedDto = service.updateIncreasedocument(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Increase Document", description = "Deletes an Increase Document by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Increase Document deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteIncreasedocument(
            @Parameter(description = "ID of the Increase Document to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteIncreasedocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Increase Document by ID", description = "Retrieves a single Increase Document by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Increase Document by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncreasedocumentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Increase Document not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<IncreasedocumentDTO> getIncreasedocumentById(
            @Parameter(description = "ID of the Increase Document to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        IncreasedocumentDTO dto = service.getIncreasedocumentById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Increase Documents", description = "Retrieves a paginated list of all Increase Documents. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Increase Documents",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<IncreasedocumentDTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<IncreasedocumentDTO>> getAllIncreasedocuments(Pageable pageable) {
        Page<IncreasedocumentDTO> dtos = service.getAllIncreasedocuments(pageable);
        return ResponseEntity.ok(dtos);
    }
}