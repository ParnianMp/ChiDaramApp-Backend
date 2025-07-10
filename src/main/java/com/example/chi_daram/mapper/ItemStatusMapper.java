package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.ItemStatusDTO;
import com.example.chi_daram.entity.ItemStatus;
import org.springframework.stereotype.Component;

@Component
public class ItemStatusMapper {

    public ItemStatus toEntity(ItemStatusDTO dto) {
        if (dto == null) {
            return null;
        }
        ItemStatus entity = new ItemStatus();
        entity.setId(dto.getId());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public ItemStatusDTO toDTO(ItemStatus entity) {
        if (entity == null) {
            return null;
        }
        ItemStatusDTO dto = new ItemStatusDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}