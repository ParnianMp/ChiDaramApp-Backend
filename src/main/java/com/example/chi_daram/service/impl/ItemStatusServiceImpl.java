package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.ItemStatusDTO;
import com.example.chi_daram.entity.ItemStatus;
import com.example.chi_daram.mapper.ItemStatusMapper;
import com.example.chi_daram.repository.ItemStatusRepository;
import com.example.chi_daram.service.ItemStatusService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemStatusServiceImpl implements ItemStatusService {

    private final ItemStatusRepository repository;
    private final ItemStatusMapper mapper;

    public ItemStatusServiceImpl(ItemStatusRepository repository, ItemStatusMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ItemStatusDTO createItemStatus(ItemStatusDTO dto) {
        // Validation: Status name cannot be null or empty
        if (dto.getStatus() == null || dto.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Item status name cannot be null or empty.");
        }
        // Validation: Check if a status with the same name already exists
        if (repository.existsByStatus(dto.getStatus())) {
            throw new IllegalArgumentException("Item status with name '" + dto.getStatus() + "' already exists.");
        }

        ItemStatus entity = mapper.toEntity(dto);
        ItemStatus savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public ItemStatusDTO updateItemStatus(Long id, ItemStatusDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        // Find the existing entity or throw an exception
        ItemStatus entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemStatus not found with id: " + id));

        // Validation: Status name cannot be null or empty for update
        if (dto.getStatus() == null || dto.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Item status name cannot be null or empty.");
        }

        // Validation: Check if a status with the updated name already exists for a different ID
        if (repository.existsByStatus(dto.getStatus()) && !entity.getStatus().equalsIgnoreCase(dto.getStatus())) {
            throw new IllegalArgumentException("Another ItemStatus with name '" + dto.getStatus() + "' already exists.");
        }

        // Update the entity's properties
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());

        // Save the updated entity and return its DTO
        ItemStatus updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteItemStatus(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        // Check if the entity exists before deleting
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ItemStatus not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public ItemStatusDTO getItemStatusById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        // Find by ID, map to DTO, or throw exception if not found
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("ItemStatus not found with id: " + id));
    }

    @Override
    public Page<ItemStatusDTO> getAllItemStatuses(Pageable pageable) {
        // Find all entities and map the page content to DTOs
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}