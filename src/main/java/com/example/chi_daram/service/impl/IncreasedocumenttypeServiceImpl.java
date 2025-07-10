package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.IncreasedocumenttypeDTO;
import com.example.chi_daram.entity.Increasedocumenttype;
import com.example.chi_daram.mapper.IncreasedocumenttypeMapper;
import com.example.chi_daram.repository.IncreasedocumenttypeRepository;
import com.example.chi_daram.service.IncreasedocumenttypeService;
import com.example.chi_daram.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncreasedocumenttypeServiceImpl implements IncreasedocumenttypeService {

    private final IncreasedocumenttypeRepository repository;
    private final IncreasedocumenttypeMapper mapper;

    public IncreasedocumenttypeServiceImpl(IncreasedocumenttypeRepository repository, IncreasedocumenttypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public IncreasedocumenttypeDTO createIncreasedocumenttype(IncreasedocumenttypeDTO dto) {
        // Validation: Title cannot be null or empty
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Increase document type title cannot be null or empty.");
        }
        // Validation: Check for duplicate title
        if (repository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("Increase document type with title '" + dto.getTitle() + "' already exists.");
        }

        Increasedocumenttype entity = mapper.toEntity(dto);
        Increasedocumenttype savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public IncreasedocumenttypeDTO updateIncreasedocumenttype(Long id, IncreasedocumenttypeDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        Increasedocumenttype entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Increasedocumenttype not found with id: " + id));

        // Validation: Title cannot be null or empty for update
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Increase document type title cannot be null or empty.");
        }

        // Validation: Check for duplicate title, excluding the current entity itself
        if (repository.existsByTitle(dto.getTitle()) && !entity.getTitle().equalsIgnoreCase(dto.getTitle())) {
            throw new IllegalArgumentException("Another Increasedocumenttype with title '" + dto.getTitle() + "' already exists.");
        }

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        Increasedocumenttype updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteIncreasedocumenttype(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Increasedocumenttype not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public IncreasedocumenttypeDTO getIncreasedocumenttypeById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Increasedocumenttype not found with id: " + id));
    }

    @Override
    public Page<IncreasedocumenttypeDTO> getAllIncreasedocumenttypes(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}