package com.example.chi_daram.service;

import com.example.chi_daram.dto.DecreasedocumentrowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DecreasedocumentrowService {

    DecreasedocumentrowDTO createDecreasedocumentrow(DecreasedocumentrowDTO dto);
    DecreasedocumentrowDTO updateDecreasedocumentrow(Long id, DecreasedocumentrowDTO dto);
    void deleteDecreasedocumentrow(Long id);
    DecreasedocumentrowDTO getDecreasedocumentrowById(Long id);
    Page<DecreasedocumentrowDTO> getAllDecreasedocumentrows(Pageable pageable);
}