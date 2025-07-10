package com.example.chi_daram.service;

import com.example.chi_daram.dto.Categorylevel2DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Categorylevel2Service {

    Categorylevel2DTO createCategorylevel2(Categorylevel2DTO dto);
    Categorylevel2DTO updateCategorylevel2(Long id, Categorylevel2DTO dto);
    void deleteCategorylevel2(Long id);
    Categorylevel2DTO getCategorylevel2ById(Long id);
    Page<Categorylevel2DTO> getAllCategorylevel2s(Pageable pageable);
}