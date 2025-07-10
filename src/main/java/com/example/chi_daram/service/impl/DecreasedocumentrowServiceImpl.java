package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.DecreasedocumentrowDTO;
import com.example.chi_daram.entity.Decreasedocument;
import com.example.chi_daram.entity.Decreasedocumentrow;
import com.example.chi_daram.entity.Item;
import com.example.chi_daram.mapper.DecreasedocumentrowMapper;
import com.example.chi_daram.repository.DecreasedocumentRepository;
import com.example.chi_daram.repository.DecreasedocumentrowRepository;
import com.example.chi_daram.repository.ItemRepository;
import com.example.chi_daram.service.DecreasedocumentrowService;
import com.example.chi_daram.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DecreasedocumentrowServiceImpl implements DecreasedocumentrowService {

    private final DecreasedocumentrowRepository repository;
    private final DecreasedocumentRepository decreasedocumentRepository;
    private final ItemRepository itemRepository;
    private final DecreasedocumentrowMapper mapper;

    public DecreasedocumentrowServiceImpl(DecreasedocumentrowRepository repository,
                                          DecreasedocumentRepository decreasedocumentRepository,
                                          ItemRepository itemRepository,
                                          DecreasedocumentrowMapper mapper) {
        this.repository = repository;
        this.decreasedocumentRepository = decreasedocumentRepository;
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    @Override
    public DecreasedocumentrowDTO createDecreasedocumentrow(DecreasedocumentrowDTO dto) {
        // Validation: DecreasedocumentId must be provided and exist
        if (dto.getDecreasedocumentId() == null) {
            throw new IllegalArgumentException("Decrease Document ID is required for a Decreasedocumentrow.");
        }
        Decreasedocument decreasedocument = decreasedocumentRepository.findById(dto.getDecreasedocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Decreasedocument not found with id: " + dto.getDecreasedocumentId()));

        // Validation: ItemId must be provided and exist
        if (dto.getItemId() == null) {
            throw new IllegalArgumentException("Item ID is required for a Decreasedocumentrow.");
        }
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + dto.getItemId()));

        // Validation: Quantity must be non-null and positive
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }
        // UnitPrice can be null as per schema

        Decreasedocumentrow entity = mapper.toEntity(dto);
        entity.setDecreasedocument(decreasedocument); // Set the fetched Decreasedocument entity
        entity.setItem(item); // Set the fetched Item entity

        Decreasedocumentrow savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public DecreasedocumentrowDTO updateDecreasedocumentrow(Long id, DecreasedocumentrowDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        // Find the existing entity or throw an exception
        Decreasedocumentrow entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Decreasedocumentrow not found with id: " + id));

        // Validation: Quantity must be non-null and positive for update
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }

        // If DecreasedocumentId is provided and different, fetch and update the association
        if (dto.getDecreasedocumentId() != null && (entity.getDecreasedocument() == null || !dto.getDecreasedocumentId().equals(entity.getDecreasedocument().getId()))) {
            Decreasedocument newDecreasedocument = decreasedocumentRepository.findById(dto.getDecreasedocumentId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Decreasedocument not found with id: " + dto.getDecreasedocumentId()));
            entity.setDecreasedocument(newDecreasedocument);
        }

        // If ItemId is provided and different, fetch and update the association
        if (dto.getItemId() != null && (entity.getItem() == null || !dto.getItemId().equals(entity.getItem().getId()))) {
            Item newItem = itemRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Item not found with id: " + dto.getItemId()));
            entity.setItem(newItem);
        }

        // Update the entity's properties
        entity.setQuantity(dto.getQuantity());
        entity.setDescription(dto.getDescription());
        entity.setUnitPrice(dto.getUnitPrice());

        Decreasedocumentrow updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteDecreasedocumentrow(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        // Check if the entity exists before deleting
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Decreasedocumentrow not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public DecreasedocumentrowDTO getDecreasedocumentrowById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        // Find by ID, map to DTO, or throw exception if not found
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Decreasedocumentrow not found with id: " + id));
    }

    @Override
    public Page<DecreasedocumentrowDTO> getAllDecreasedocumentrows(Pageable pageable) {
        // Find all entities and map the page content to DTOs
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}