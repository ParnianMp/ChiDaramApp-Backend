package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.DecreasedocumentDTO;
import com.example.chi_daram.entity.Decreasedocument;
import com.example.chi_daram.entity.Decreasedocumenttype;
import com.example.chi_daram.entity.User;
import com.example.chi_daram.mapper.DecreasedocumentMapper;
import com.example.chi_daram.repository.DecreasedocumentRepository;
import com.example.chi_daram.repository.DecreasedocumenttypeRepository;
import com.example.chi_daram.repository.UserRepository;
import com.example.chi_daram.service.DecreasedocumentService;
import com.example.chi_daram.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DecreasedocumentServiceImpl implements DecreasedocumentService {

    private final DecreasedocumentRepository repository;
    private final DecreasedocumenttypeRepository decreasedocumenttypeRepository;
    private final UserRepository userRepository;
    private final DecreasedocumentMapper mapper;

    public DecreasedocumentServiceImpl(DecreasedocumentRepository repository,
                                      DecreasedocumenttypeRepository decreasedocumenttypeRepository,
                                      UserRepository userRepository,
                                      DecreasedocumentMapper mapper) {
        this.repository = repository;
        this.decreasedocumenttypeRepository = decreasedocumenttypeRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public DecreasedocumentDTO createDecreasedocument(DecreasedocumentDTO dto) {
        // Validation: Document number cannot be null or empty
        if (dto.getDocumentNumber() == null || dto.getDocumentNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Document number cannot be null or empty.");
        }
        // Validation: Document number must be unique
        if (repository.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new IllegalArgumentException("Decrease document with document number '" + dto.getDocumentNumber() + "' already exists.");
        }
        // Validation: DecreaseTypeId must be provided and exist
        if (dto.getDecreaseTypeId() == null) {
            throw new IllegalArgumentException("Decrease document type ID is required.");
        }
        Decreasedocumenttype decreaseType = decreasedocumenttypeRepository.findById(dto.getDecreaseTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Decreasedocumenttype not found with id: " + dto.getDecreaseTypeId()));

        // Validation: UserId must be provided and exist
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        // Validation: DecreaseDate must not be null
        if (dto.getDecreaseDate() == null) {
            throw new IllegalArgumentException("Decrease date cannot be null.");
        }

        Decreasedocument entity = mapper.toEntity(dto);
        entity.setDecreaseType(decreaseType); // Set the fetched Decreasedocumenttype entity
        entity.setUser(user); // Set the fetched User entity

        Decreasedocument savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public DecreasedocumentDTO updateDecreasedocument(Long id, DecreasedocumentDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        Decreasedocument entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Decreasedocument not found with id: " + id));

        // Validation: Document number cannot be null or empty for update
        if (dto.getDocumentNumber() == null || dto.getDocumentNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Document number cannot be null or empty.");
        }
        // Validation: Check for duplicate document number, excluding the current entity itself
        if (repository.existsByDocumentNumber(dto.getDocumentNumber()) && !entity.getDocumentNumber().equalsIgnoreCase(dto.getDocumentNumber())) {
            throw new IllegalArgumentException("Another Decreasedocument with document number '" + dto.getDocumentNumber() + "' already exists.");
        }

        // If DecreaseTypeId is provided and different, fetch and update the association
        if (dto.getDecreaseTypeId() != null && (entity.getDecreaseType() == null || !dto.getDecreaseTypeId().equals(entity.getDecreaseType().getId()))) {
            Decreasedocumenttype newDecreaseType = decreasedocumenttypeRepository.findById(dto.getDecreaseTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Decreasedocumenttype not found with id: " + dto.getDecreaseTypeId()));
            entity.setDecreaseType(newDecreaseType);
        }

        // If UserId is provided and different, fetch and update the association
        if (dto.getUserId() != null && (entity.getUser() == null || !dto.getUserId().equals(entity.getUser().getId()))) {
            User newUser = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("New User not found with id: " + dto.getUserId()));
            entity.setUser(newUser);
        }

        // Validation: DecreaseDate must not be null for update
        if (dto.getDecreaseDate() == null) {
            throw new IllegalArgumentException("Decrease date cannot be null.");
        }

        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setDecreaseDate(dto.getDecreaseDate());
        entity.setDescription(dto.getDescription());

        Decreasedocument updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteDecreasedocument(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Decreasedocument not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public DecreasedocumentDTO getDecreasedocumentById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Decreasedocument not found with id: " + id));
    }

    @Override
    public Page<DecreasedocumentDTO> getAllDecreasedocuments(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}