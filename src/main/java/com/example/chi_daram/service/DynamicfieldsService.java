package com.example.chi_daram.service;

import com.example.chi_daram.dto.DynamicfieldsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DynamicfieldsService {

    DynamicfieldsDTO createDynamicfields(DynamicfieldsDTO dto);
    DynamicfieldsDTO updateDynamicfields(Long id, DynamicfieldsDTO dto);
    void deleteDynamicfields(Long id);
    DynamicfieldsDTO getDynamicfieldsById(Long id);
    Page<DynamicfieldsDTO> getAllDynamicfields(Pageable pageable);
}