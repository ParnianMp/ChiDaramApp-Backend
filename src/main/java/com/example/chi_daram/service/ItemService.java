package com.example.chi_daram.service;

import com.example.chi_daram.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    ItemDTO createItem(ItemDTO dto);
    ItemDTO updateItem(Long id, ItemDTO dto);
    void deleteItem(Long id);
    ItemDTO getItemById(Long id);
    Page<ItemDTO> getAllItems(Pageable pageable);
}