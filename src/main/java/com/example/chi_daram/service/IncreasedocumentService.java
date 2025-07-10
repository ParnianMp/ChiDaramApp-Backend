package com.example.chi_daram.service;

import com.example.chi_daram.dto.IncreasedocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncreasedocumentService {

    IncreasedocumentDTO createIncreasedocument(IncreasedocumentDTO dto);
    IncreasedocumentDTO updateIncreasedocument(Long id, IncreasedocumentDTO dto);
    void deleteIncreasedocument(Long id);
    IncreasedocumentDTO getIncreasedocumentById(Long id);
    Page<IncreasedocumentDTO> getAllIncreasedocuments(Pageable pageable);
}