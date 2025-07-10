package com.example.chi_daram.service;

import com.example.chi_daram.dto.DecreasedocumenttypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DecreasedocumenttypeService {

    DecreasedocumenttypeDTO createDecreasedocumenttype(DecreasedocumenttypeDTO dto);
    DecreasedocumenttypeDTO updateDecreasedocumenttype(Long id, DecreasedocumenttypeDTO dto);
    void deleteDecreasedocumenttype(Long id);
    DecreasedocumenttypeDTO getDecreasedocumenttypeById(Long id);
    Page<DecreasedocumenttypeDTO> getAllDecreasedocumenttypes(Pageable pageable);
}