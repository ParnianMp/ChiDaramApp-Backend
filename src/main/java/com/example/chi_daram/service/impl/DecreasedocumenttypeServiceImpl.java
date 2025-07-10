package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.DecreasedocumenttypeDTO;
import com.example.chi_daram.entity.Decreasedocumenttype;
import com.example.chi_daram.mapper.DecreasedocumenttypeMapper;
import com.example.chi_daram.repository.DecreasedocumenttypeRepository;
import com.example.chi_daram.service.DecreasedocumenttypeService;
import com.example.chi_daram.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DecreasedocumenttypeServiceImpl implements DecreasedocumenttypeService {

    private final DecreasedocumenttypeRepository repository;
    private final DecreasedocumenttypeMapper mapper;

    public DecreasedocumenttypeServiceImpl(DecreasedocumenttypeRepository repository, DecreasedocumenttypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DecreasedocumenttypeDTO createDecreasedocumenttype(DecreasedocumenttypeDTO dto) {
        // Validation: Title cannot be null or empty
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Decrease document type title cannot be null or empty.");
        }
        // Validation: Check for duplicate title
        if (repository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("Decrease document type with title '" + dto.getTitle() + "' already exists.");
        }

        Decreasedocumenttype entity = mapper.toEntity(dto);
        Decreasedocumenttype savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public DecreasedocumenttypeDTO updateDecreasedocumenttype(Long id, DecreasedocumenttypeDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        Decreasedocumenttype entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Decreasedocumenttype not found with id: " + id));

        // Validation: Title cannot be null or empty for update
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Decrease document type title cannot be null or empty.");
        }

        // Validation: Check for duplicate title, excluding the current entity itself
        if (repository.existsByTitle(dto.getTitle()) && !entity.getTitle().equalsIgnoreCase(dto.getTitle())) {
            throw new IllegalArgumentException("Another Decreasedocumenttype with title '" + dto.getTitle() + "' already exists.");
        }

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        Decreasedocumenttype updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteDecreasedocumenttype(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Decreasedocumenttype not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public DecreasedocumenttypeDTO getDecreasedocumenttypeById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Decreasedocumenttype not found with id: " + id));
    }

    @Override
    public Page<DecreasedocumenttypeDTO> getAllDecreasedocumenttypes(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}