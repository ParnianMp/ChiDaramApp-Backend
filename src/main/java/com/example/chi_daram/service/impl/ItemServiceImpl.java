package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.ItemDTO;
import com.example.chi_daram.entity.Categorylevel2;
import com.example.chi_daram.entity.Item;
import com.example.chi_daram.mapper.ItemMapper;
import com.example.chi_daram.repository.Categorylevel2Repository; // Needed to fetch Categorylevel2
import com.example.chi_daram.repository.ItemRepository;
import com.example.chi_daram.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final Categorylevel2Repository categorylevel2Repository; // Inject Categorylevel2Repository
    private final ItemMapper mapper;

    public ItemServiceImpl(ItemRepository repository,
                           Categorylevel2Repository categorylevel2Repository,
                           ItemMapper mapper) {
        this.repository = repository;
        this.categorylevel2Repository = categorylevel2Repository;
        this.mapper = mapper;
    }

    @Override
    public ItemDTO createItem(ItemDTO dto) {
        // Validation: Name cannot be null or empty
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        // Validation: Quantity must be non-negative
        if (dto.getQuantity() == null || dto.getQuantity() < 0) {
            throw new IllegalArgumentException("Item quantity cannot be null or negative.");
        }
        // Validation: Categorylevel2Id must be provided
        if (dto.getCategorylevel2Id() == null) {
            throw new IllegalArgumentException("Categorylevel2 ID is required for an Item.");
        }

        // Fetch Categorylevel2 to link with the Item
        Categorylevel2 categorylevel2 = categorylevel2Repository.findById(dto.getCategorylevel2Id())
                .orElseThrow(() -> new EntityNotFoundException("Categorylevel2 not found with id: " + dto.getCategorylevel2Id()));

        // Validation: Check if an item with the same name already exists (optional, depends on business rule)
        // A more robust check might be `repository.existsByNameAndCategorylevel2(dto.getName(), categorylevel2)`
        if (repository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Item with name '" + dto.getName() + "' already exists.");
        }

        Item entity = mapper.toEntity(dto);
        entity.setCategorylevel2(categorylevel2); // Set the fetched Categorylevel2 entity
        Item savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public ItemDTO updateItem(Long id, ItemDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        // Find the existing entity or throw an exception
        Item entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));

        // Validation: Name cannot be null or empty for update
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        // Validation: Quantity must be non-negative for update
        if (dto.getQuantity() == null || dto.getQuantity() < 0) {
            throw new IllegalArgumentException("Item quantity cannot be null or negative.");
        }

        // If Categorylevel2Id is provided and different, fetch and update the association
        if (dto.getCategorylevel2Id() != null && (entity.getCategorylevel2() == null || !dto.getCategorylevel2Id().equals(entity.getCategorylevel2().getId()))) {
            Categorylevel2 newCategorylevel2 = categorylevel2Repository.findById(dto.getCategorylevel2Id())
                    .orElseThrow(() -> new EntityNotFoundException("New Categorylevel2 not found with id: " + dto.getCategorylevel2Id()));
            entity.setCategorylevel2(newCategorylevel2);
        }

        // Validation: Check if an item with the updated name already exists for a different ID
        if (repository.existsByName(dto.getName()) && !entity.getName().equalsIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Another Item with name '" + dto.getName() + "' already exists.");
        }

        // Update the entity's properties
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setColor(dto.getColor());
        entity.setSize(dto.getSize());
        entity.setImage(dto.getImage());

        // Save the updated entity and return its DTO
        Item updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteItem(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        // Check if the entity exists before deleting
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public ItemDTO getItemById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        // Find by ID, map to DTO, or throw exception if not found
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
    }

    @Override
    public Page<ItemDTO> getAllItems(Pageable pageable) {
        // Find all entities and map the page content to DTOs
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}