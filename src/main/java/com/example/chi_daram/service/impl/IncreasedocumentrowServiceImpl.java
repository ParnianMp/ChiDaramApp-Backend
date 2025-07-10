package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.IncreasedocumentrowDTO;
import com.example.chi_daram.entity.Increasedocument;
import com.example.chi_daram.entity.Increasedocumentrow;
import com.example.chi_daram.entity.Item;
import com.example.chi_daram.mapper.IncreasedocumentrowMapper;
import com.example.chi_daram.repository.IncreasedocumentRepository; // Now using the correct IncreasedocumentRepository
import com.example.chi_daram.repository.IncreasedocumentrowRepository;
import com.example.chi_daram.repository.ItemRepository; // Using the existing ItemRepository
import com.example.chi_daram.service.IncreasedocumentrowService;
import com.example.chi_daram.exception.ResourceNotFoundException; // Using your custom exception
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncreasedocumentrowServiceImpl implements IncreasedocumentrowService {

    private final IncreasedocumentrowRepository repository;
    private final IncreasedocumentRepository increasedocumentRepository; // Correct repository
    private final ItemRepository itemRepository; // Existing repository
    private final IncreasedocumentrowMapper mapper;

    public IncreasedocumentrowServiceImpl(IncreasedocumentrowRepository repository,
                                          IncreasedocumentRepository increasedocumentRepository,
                                          ItemRepository itemRepository,
                                          IncreasedocumentrowMapper mapper) {
        this.repository = repository;
        this.increasedocumentRepository = increasedocumentRepository;
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    @Override
    public IncreasedocumentrowDTO createIncreasedocumentrow(IncreasedocumentrowDTO dto) {
        // Validation: IncreasedocumentId must be provided and exist
        if (dto.getIncreasedocumentId() == null) {
            throw new IllegalArgumentException("Increase Document ID is required for an Increasedocumentrow.");
        }
        Increasedocument increasedocument = increasedocumentRepository.findById(dto.getIncreasedocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Increasedocument not found with id: " + dto.getIncreasedocumentId()));

        // Validation: ItemId must be provided and exist
        if (dto.getItemId() == null) {
            throw new IllegalArgumentException("Item ID is required for an Increasedocumentrow.");
        }
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + dto.getItemId()));

        // Validation: Quantity must be non-null and positive
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }
        // UnitPrice can be null as per schema

        Increasedocumentrow entity = mapper.toEntity(dto);
        entity.setIncreasedocument(increasedocument); // Set the fetched Increasedocument entity
        entity.setItem(item); // Set the fetched Item entity

        Increasedocumentrow savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public IncreasedocumentrowDTO updateIncreasedocumentrow(Long id, IncreasedocumentrowDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        // Find the existing entity or throw an exception
        Increasedocumentrow entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Increasedocumentrow not found with id: " + id));

        // Validation: Quantity must be non-null and positive for update
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }

        // If IncreasedocumentId is provided and different, fetch and update the association
        if (dto.getIncreasedocumentId() != null && (entity.getIncreasedocument() == null || !dto.getIncreasedocumentId().equals(entity.getIncreasedocument().getId()))) {
            Increasedocument newIncreasedocument = increasedocumentRepository.findById(dto.getIncreasedocumentId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Increasedocument not found with id: " + dto.getIncreasedocumentId()));
            entity.setIncreasedocument(newIncreasedocument);
        }

        // If ItemId is provided and different, fetch and update the association
        if (dto.getItemId() != null && (entity.getItem() == null || !dto.getItemId().equals(entity.getItem().getId()))) {
            Item newItem = itemRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Item not found with id: " + dto.getItemId()));
            entity.setItem(newItem);
        }

        // Update the entity's properties
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setDescription(dto.getDescription());

        Increasedocumentrow updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteIncreasedocumentrow(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        // Check if the entity exists before deleting
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Increasedocumentrow not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public IncreasedocumentrowDTO getIncreasedocumentrowById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        // Find by ID, map to DTO, or throw exception if not found
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Increasedocumentrow not found with id: " + id));
    }

    @Override
    public Page<IncreasedocumentrowDTO> getAllIncreasedocumentrows(Pageable pageable) {
        // Find all entities and map the page content to DTOs
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}