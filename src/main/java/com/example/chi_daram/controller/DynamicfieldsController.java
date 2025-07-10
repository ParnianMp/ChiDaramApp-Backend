package com.example.chi_daram.controller;

import com.example.chi_daram.dto.DynamicfieldsDTO;
import com.example.chi_daram.service.DynamicfieldsService;
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
@RequestMapping("/api/dynamicfields")
@Tag(name = "Dynamic Fields Management", description = "APIs for managing Dynamic Fields entities")
public class DynamicfieldsController {

    private final DynamicfieldsService service;

    public DynamicfieldsController(DynamicfieldsService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Dynamic Field", description = "Adds a new Dynamic Field to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dynamic Field created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DynamicfieldsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Related Item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Dynamic Field already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DynamicfieldsDTO> createDynamicfields(
            @RequestBody(description = "Dynamic Field details to create", required = true,
                    content = @Content(schema = @Schema(implementation = DynamicfieldsDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody DynamicfieldsDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DynamicfieldsDTO createdDto = service.createDynamicfields(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Dynamic Field", description = "Updates the details of an existing Dynamic Field. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dynamic Field updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DynamicfieldsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Dynamic Field not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DynamicfieldsDTO> updateDynamicfields(
            @Parameter(description = "ID of the Dynamic Field to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Dynamic Field details for update", required = true,
                    content = @Content(schema = @Schema(implementation = DynamicfieldsDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody DynamicfieldsDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DynamicfieldsDTO updatedDto = service.updateDynamicfields(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Dynamic Field", description = "Deletes a Dynamic Field by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dynamic Field deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Dynamic Field not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteDynamicfields(
            @Parameter(description = "ID of the Dynamic Field to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteDynamicfields(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Dynamic Field by ID", description = "Retrieves a single Dynamic Field by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Dynamic Field by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DynamicfieldsDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Dynamic Field not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<DynamicfieldsDTO> getDynamicfieldsById(
            @Parameter(description = "ID of the Dynamic Field to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        DynamicfieldsDTO dto = service.getDynamicfieldsById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Dynamic Fields", description = "Retrieves a paginated list of all Dynamic Fields. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Dynamic Fields",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<DynamicfieldsDTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<DynamicfieldsDTO>> getAllDynamicfields(Pageable pageable) {
        Page<DynamicfieldsDTO> dtos = service.getAllDynamicfields(pageable);
        return ResponseEntity.ok(dtos);
    }
}