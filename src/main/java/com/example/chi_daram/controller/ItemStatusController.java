package com.example.chi_daram.controller;

import com.example.chi_daram.dto.ItemStatusDTO;
import com.example.chi_daram.service.ItemStatusService;
// import jakarta.persistence.EntityNotFoundException; // توسط GlobalExceptionHandler مدیریت می‌شود
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
@RequestMapping("/api/itemstatuses")
@Tag(name = "Item Status Management", description = "APIs for managing Item Status entities")
public class ItemStatusController {

    private final ItemStatusService service;

    public ItemStatusController(ItemStatusService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new Item Status", description = "Adds a new Item Status to the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item Status created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemStatusDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "409", description = "Item Status already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<ItemStatusDTO> createItemStatus(
            @RequestBody(description = "Item Status details to create", required = true,
                    content = @Content(schema = @Schema(implementation = ItemStatusDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody ItemStatusDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        ItemStatusDTO createdDto = service.createItemStatus(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Item Status", description = "Updates the details of an existing Item Status. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item Status updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemStatusDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Item Status not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Item Status already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<ItemStatusDTO> updateItemStatus(
            @Parameter(description = "ID of the Item Status to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Item Status details for update", required = true,
                    content = @Content(schema = @Schema(implementation = ItemStatusDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody ItemStatusDTO dto) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        ItemStatusDTO updatedDto = service.updateItemStatus(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Item Status", description = "Deletes an Item Status by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item Status deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Item Status not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<Void> deleteItemStatus(
            @Parameter(description = "ID of the Item Status to delete", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        service.deleteItemStatus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Item Status by ID", description = "Retrieves a single Item Status by its ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Item Status by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemStatusDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Item Status not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<ItemStatusDTO> getItemStatusById(
            @Parameter(description = "ID of the Item Status to retrieve", required = true)
            @PathVariable Long id) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        ItemStatusDTO dto = service.getItemStatusById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Get all Item Statuses", description = "Retrieves a paginated list of all Item Statuses. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Item Statuses",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))), // Page<ItemStatusDTO>
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<Page<ItemStatusDTO>> getAllItemStatuses(Pageable pageable) {
        Page<ItemStatusDTO> dtos = service.getAllItemStatuses(pageable);
        return ResponseEntity.ok(dtos);
    }
}