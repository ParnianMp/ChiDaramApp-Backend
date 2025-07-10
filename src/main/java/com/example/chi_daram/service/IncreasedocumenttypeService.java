package com.example.chi_daram.service;

import com.example.chi_daram.dto.IncreasedocumenttypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncreasedocumenttypeService {

    IncreasedocumenttypeDTO createIncreasedocumenttype(IncreasedocumenttypeDTO dto);
    IncreasedocumenttypeDTO updateIncreasedocumenttype(Long id, IncreasedocumenttypeDTO dto);
    void deleteIncreasedocumenttype(Long id);
    IncreasedocumenttypeDTO getIncreasedocumenttypeById(Long id);
    Page<IncreasedocumenttypeDTO> getAllIncreasedocumenttypes(Pageable pageable);
}