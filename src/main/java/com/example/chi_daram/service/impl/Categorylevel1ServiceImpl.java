package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.Categorylevel1DTO;
import com.example.chi_daram.entity.Categorylevel1;
import com.example.chi_daram.mapper.Categorylevel1Mapper;
import com.example.chi_daram.repository.Categorylevel1Repository; // Assuming this is your repository
import com.example.chi_daram.service.Categorylevel1Service;
import jakarta.persistence.EntityNotFoundException; // A more specific exception
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class Categorylevel1ServiceImpl implements Categorylevel1Service {

    private final Categorylevel1Repository repository;
    private final Categorylevel1Mapper mapper;

    // Dependency Injection via constructor
    public Categorylevel1ServiceImpl(Categorylevel1Repository repository, Categorylevel1Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Categorylevel1DTO createCategorylevel1(Categorylevel1DTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        Categorylevel1 entity = mapper.toEntity(dto);
        Categorylevel1 savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public Categorylevel1DTO updateCategorylevel1(Long id, Categorylevel1DTO dto) {
        // Find the existing entity or throw an exception
        Categorylevel1 entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categorylevel1 not found with id: " + id));

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }

        // Update the entity's properties
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        // Save the updated entity and return its DTO
        Categorylevel1 updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteCategorylevel1(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Categorylevel1 not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Categorylevel1DTO getCategorylevel1ById(Long id) {
        // Find by ID, map to DTO, or throw exception if not found
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Categorylevel1 not found with id: " + id)); // اصلاح شده: _id به id تغییر یافت
    }

    @Override
    public Page<Categorylevel1DTO> getAllCategorylevel1s(Pageable pageable) {
        // Find all entities and map the page content to DTOs
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}