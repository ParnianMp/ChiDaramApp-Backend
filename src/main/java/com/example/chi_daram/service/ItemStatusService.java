package com.example.chi_daram.service;

import com.example.chi_daram.dto.ItemStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemStatusService {

    ItemStatusDTO createItemStatus(ItemStatusDTO dto);
    ItemStatusDTO updateItemStatus(Long id, ItemStatusDTO dto);
    void deleteItemStatus(Long id);
    ItemStatusDTO getItemStatusById(Long id);
    Page<ItemStatusDTO> getAllItemStatuses(Pageable pageable);
}