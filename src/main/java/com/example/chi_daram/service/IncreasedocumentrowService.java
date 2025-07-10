package com.example.chi_daram.service;

import com.example.chi_daram.dto.IncreasedocumentrowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncreasedocumentrowService {

    IncreasedocumentrowDTO createIncreasedocumentrow(IncreasedocumentrowDTO dto);
    IncreasedocumentrowDTO updateIncreasedocumentrow(Long id, IncreasedocumentrowDTO dto);
    void deleteIncreasedocumentrow(Long id);
    IncreasedocumentrowDTO getIncreasedocumentrowById(Long id);
    Page<IncreasedocumentrowDTO> getAllIncreasedocumentrows(Pageable pageable);
}