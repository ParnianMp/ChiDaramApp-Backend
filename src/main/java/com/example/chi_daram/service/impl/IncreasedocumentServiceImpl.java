package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.IncreasedocumentDTO;
import com.example.chi_daram.entity.Increasedocument;
import com.example.chi_daram.entity.Increasedocumenttype;
import com.example.chi_daram.entity.User; // Assuming User entity exists
import com.example.chi_daram.mapper.IncreasedocumentMapper;
import com.example.chi_daram.repository.IncreasedocumentRepository;
import com.example.chi_daram.repository.IncreasedocumenttypeRepository; // Needed to fetch Increasedocumenttype
import com.example.chi_daram.repository.UserRepository; // Needed to fetch User (assuming UserRepository exists)
import com.example.chi_daram.service.IncreasedocumentService;
import com.example.chi_daram.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncreasedocumentServiceImpl implements IncreasedocumentService {

    private final IncreasedocumentRepository repository;
    private final IncreasedocumenttypeRepository increasedocumenttypeRepository;
    private final UserRepository userRepository; // Inject UserRepository
    private final IncreasedocumentMapper mapper;

    public IncreasedocumentServiceImpl(IncreasedocumentRepository repository,
                                      IncreasedocumenttypeRepository increasedocumenttypeRepository,
                                      UserRepository userRepository,
                                      IncreasedocumentMapper mapper) {
        this.repository = repository;
        this.increasedocumenttypeRepository = increasedocumenttypeRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public IncreasedocumentDTO createIncreasedocument(IncreasedocumentDTO dto) {
        // Validation: Document number cannot be null or empty
        if (dto.getDocumentNumber() == null || dto.getDocumentNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Document number cannot be null or empty.");
        }
        // Validation: Document number must be unique
        if (repository.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new IllegalArgumentException("Increase document with document number '" + dto.getDocumentNumber() + "' already exists.");
        }
        // Validation: IncreaseTypeId must be provided and exist
        if (dto.getIncreaseTypeId() == null) {
            throw new IllegalArgumentException("Increase document type ID is required.");
        }
        Increasedocumenttype increaseType = increasedocumenttypeRepository.findById(dto.getIncreaseTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Increasedocumenttype not found with id: " + dto.getIncreaseTypeId()));

        // Validation: UserId must be provided and exist
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        // Validation: IncreaseDate must not be null
        if (dto.getIncreaseDate() == null) {
            throw new IllegalArgumentException("Increase date cannot be null.");
        }

        Increasedocument entity = mapper.toEntity(dto);
        entity.setIncreaseType(increaseType); // Set the fetched Increasedocumenttype entity
        entity.setUser(user); // Set the fetched User entity

        Increasedocument savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public IncreasedocumentDTO updateIncreasedocument(Long id, IncreasedocumentDTO dto) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        Increasedocument entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Increasedocument not found with id: " + id));

        // Validation: Document number cannot be null or empty for update
        if (dto.getDocumentNumber() == null || dto.getDocumentNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Document number cannot be null or empty.");
        }
        // Validation: Check for duplicate document number, excluding the current entity itself
        if (repository.existsByDocumentNumber(dto.getDocumentNumber()) && !entity.getDocumentNumber().equalsIgnoreCase(dto.getDocumentNumber())) {
            throw new IllegalArgumentException("Another Increasedocument with document number '" + dto.getDocumentNumber() + "' already exists.");
        }

        // If IncreaseTypeId is provided and different, fetch and update the association
        if (dto.getIncreaseTypeId() != null && (entity.getIncreaseType() == null || !dto.getIncreaseTypeId().equals(entity.getIncreaseType().getId()))) {
            Increasedocumenttype newIncreaseType = increasedocumenttypeRepository.findById(dto.getIncreaseTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Increasedocumenttype not found with id: " + dto.getIncreaseTypeId()));
            entity.setIncreaseType(newIncreaseType);
        }

        // If UserId is provided and different, fetch and update the association
        if (dto.getUserId() != null && (entity.getUser() == null || !dto.getUserId().equals(entity.getUser().getId()))) {
            User newUser = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("New User not found with id: " + dto.getUserId()));
            entity.setUser(newUser);
        }

        // Validation: IncreaseDate must not be null for update
        if (dto.getIncreaseDate() == null) {
            throw new IllegalArgumentException("Increase date cannot be null.");
        }

        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setIncreaseDate(dto.getIncreaseDate());
        entity.setDescription(dto.getDescription());

        Increasedocument updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteIncreasedocument(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Increasedocument not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public IncreasedocumentDTO getIncreasedocumentById(Long id) {
        // Validation: ID must not be null
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Increasedocument not found with id: " + id));
    }

    @Override
    public Page<IncreasedocumentDTO> getAllIncreasedocuments(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}