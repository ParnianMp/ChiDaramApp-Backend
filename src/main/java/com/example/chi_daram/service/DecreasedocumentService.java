package com.example.chi_daram.service;

import com.example.chi_daram.dto.DecreasedocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DecreasedocumentService {

    DecreasedocumentDTO createDecreasedocument(DecreasedocumentDTO dto);
    DecreasedocumentDTO updateDecreasedocument(Long id, DecreasedocumentDTO dto);
    void deleteDecreasedocument(Long id);
    DecreasedocumentDTO getDecreasedocumentById(Long id);
    Page<DecreasedocumentDTO> getAllDecreasedocuments(Pageable pageable);
}