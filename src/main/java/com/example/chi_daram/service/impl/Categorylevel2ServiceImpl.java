package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.Categorylevel2DTO;
import com.example.chi_daram.entity.Categorylevel1;
import com.example.chi_daram.entity.Categorylevel2;
import com.example.chi_daram.mapper.Categorylevel2Mapper;
import com.example.chi_daram.repository.Categorylevel1Repository; // Needed to fetch Categorylevel1
import com.example.chi_daram.repository.Categorylevel2Repository;
import com.example.chi_daram.service.Categorylevel2Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class Categorylevel2ServiceImpl implements Categorylevel2Service {

    private final Categorylevel2Repository repository;
    private final Categorylevel1Repository categorylevel1Repository; // Inject Categorylevel1Repository
    private final Categorylevel2Mapper mapper;

    public Categorylevel2ServiceImpl(Categorylevel2Repository repository,
                                     Categorylevel1Repository categorylevel1Repository,
                                     Categorylevel2Mapper mapper) {
        this.repository = repository;
        this.categorylevel1Repository = categorylevel1Repository;
        this.mapper = mapper;
    }

    @Override
    public Categorylevel2DTO createCategorylevel2(Categorylevel2DTO dto) {
        // Validation: Name cannot be null or empty
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Categorylevel2 name cannot be null or empty.");
        }
        // Validation: Categorylevel1Id must be provided
        if (dto.getCategorylevel1Id() == null) {
            throw new IllegalArgumentException("Categorylevel1 ID is required for Categorylevel2.");
        }
        // Validation: Check if Categorylevel1 exists
        Categorylevel1 categorylevel1 = categorylevel1Repository.findById(dto.getCategorylevel1Id())
                .orElseThrow(() -> new EntityNotFoundException("Categorylevel1 not found with id: " + dto.getCategorylevel1Id()));
        
        // Validation: Check if a category with the same name already exists under this Categorylevel1
        // This requires a custom query in the repository or fetching all and filtering,
        // For simplicity, we'll just check if a Categorylevel2 with this name exists globally.
        // A more robust check would be: repository.existsByNameAndCategorylevel1(dto.getName(), categorylevel1);
        if (repository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Categorylevel2 with name '" + dto.getName() + "' already exists.");
        }

        Categorylevel2 entity = mapper.toEntity(dto);
        entity.setCategorylevel1(categorylevel1); // Set the fetched Categorylevel1 entity
        Categorylevel2 savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public Categorylevel2DTO updateCategorylevel2(Long id, Categorylevel2DTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        // Find the existing entity or throw an exception
        Categorylevel2 entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categorylevel2 not found with id: " + id));

        // Validation: Name cannot be null or empty for update
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Categorylevel2 name cannot be null or empty.");
        }
        
        // Validation: Categorylevel1Id must be provided for update if it's changing
        if (dto.getCategorylevel1Id() != null && !dto.getCategorylevel1Id().equals(entity.getCategorylevel1().getId())) {
             Categorylevel1 newCategorylevel1 = categorylevel1Repository.findById(dto.getCategorylevel1Id())
                .orElseThrow(() -> new EntityNotFoundException("New Categorylevel1 not found with id: " + dto.getCategorylevel1Id()));
             entity.setCategorylevel1(newCategorylevel1);
        }

        // Validation: Check if a category with the updated name already exists for a different ID
        if (repository.existsByName(dto.getName()) && !entity.getName().equalsIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Another Categorylevel2 with name '" + dto.getName() + "' already exists.");
        }

        // Update the entity's properties
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        // Save the updated entity and return its DTO
        Categorylevel2 updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteCategorylevel2(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        // Check if the entity exists before deleting
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Categorylevel2 not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Categorylevel2DTO getCategorylevel2ById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        // Find by ID, map to DTO, or throw exception if not found
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Categorylevel2 not found with id: " + id));
    }

    @Override
    public Page<Categorylevel2DTO> getAllCategorylevel2s(Pageable pageable) {
        // Find all entities and map the page content to DTOs
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}