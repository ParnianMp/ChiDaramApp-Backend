package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.DynamicfieldsDTO;
import com.example.chi_daram.entity.Dynamicfields;
import com.example.chi_daram.entity.Item;
import com.example.chi_daram.mapper.DynamicfieldsMapper;
import com.example.chi_daram.repository.DynamicfieldsRepository;
import com.example.chi_daram.repository.ItemRepository; // Needed to fetch Item
import com.example.chi_daram.service.DynamicfieldsService;
import com.example.chi_daram.exception.ResourceNotFoundException; // Using your custom exception
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DynamicfieldsServiceImpl implements DynamicfieldsService {

    private final DynamicfieldsRepository repository;
    private final ItemRepository itemRepository;
    private final DynamicfieldsMapper mapper;

    public DynamicfieldsServiceImpl(DynamicfieldsRepository repository, ItemRepository itemRepository, DynamicfieldsMapper mapper) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    @Override
    public DynamicfieldsDTO createDynamicfields(DynamicfieldsDTO dto) {
        // Validation: ItemId must be provided and exist
        if (dto.getItemId() == null) {
            throw new IllegalArgumentException("Item ID is required for Dynamicfields.");
        }
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + dto.getItemId()));

        // Description can be null as per schema

        Dynamicfields entity = mapper.toEntity(dto);
        entity.setItem(item); // Set the fetched Item entity

        Dynamicfields savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public DynamicfieldsDTO updateDynamicfields(Long id, DynamicfieldsDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        // Find the existing entity or throw an exception
        Dynamicfields entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dynamicfields not found with id: " + id));

        // If ItemId is provided and different, fetch and update the association
        if (dto.getItemId() != null && (entity.getItem() == null || !dto.getItemId().equals(entity.getItem().getId()))) {
            Item newItem = itemRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Item not found with id: " + dto.getItemId()));
            entity.setItem(newItem);
        }

        // Update the entity's properties
        entity.setDescription(dto.getDescription());

        Dynamicfields updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteDynamicfields(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        // Check if the entity exists before deleting
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Dynamicfields not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public DynamicfieldsDTO getDynamicfieldsById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        // Find by ID, map to DTO, or throw exception if not found
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Dynamicfields not found with id: " + id));
    }

    @Override
    public Page<DynamicfieldsDTO> getAllDynamicfields(Pageable pageable) {
        // Find all entities and map the page content to DTOs
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}