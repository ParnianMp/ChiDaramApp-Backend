package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.DynamicfieldsDTO;
import com.example.chi_daram.entity.Dynamicfields;
import org.springframework.stereotype.Component;

@Component
public class DynamicfieldsMapper {

    public Dynamicfields toEntity(DynamicfieldsDTO dto) {
        if (dto == null) {
            return null;
        }
        Dynamicfields entity = new Dynamicfields();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        // Item entity will be set in the service layer
        return entity;
    }

    public DynamicfieldsDTO toDTO(Dynamicfields entity) {
        if (entity == null) {
            return null;
        }
        DynamicfieldsDTO dto = new DynamicfieldsDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        if (entity.getItem() != null) {
            dto.setItemId(entity.getItem().getId());
        }
        return dto;
    }
}