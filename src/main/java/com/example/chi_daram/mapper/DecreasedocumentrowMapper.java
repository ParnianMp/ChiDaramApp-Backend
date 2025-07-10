package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.DecreasedocumentrowDTO;
import com.example.chi_daram.entity.Decreasedocumentrow;
import org.springframework.stereotype.Component;

@Component
public class DecreasedocumentrowMapper {

    public Decreasedocumentrow toEntity(DecreasedocumentrowDTO dto) {
        if (dto == null) {
            return null;
        }
        Decreasedocumentrow entity = new Decreasedocumentrow();
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setDescription(dto.getDescription());
        entity.setUnitPrice(dto.getUnitPrice());
        // Decreasedocument and Item entities will be set in the service layer
        return entity;
    }

    public DecreasedocumentrowDTO toDTO(Decreasedocumentrow entity) {
        if (entity == null) {
            return null;
        }
        DecreasedocumentrowDTO dto = new DecreasedocumentrowDTO();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setDescription(entity.getDescription());
        dto.setUnitPrice(entity.getUnitPrice());
        if (entity.getDecreasedocument() != null) {
            dto.setDecreasedocumentId(entity.getDecreasedocument().getId());
        }
        if (entity.getItem() != null) {
            dto.setItemId(entity.getItem().getId());
        }
        return dto;
    }
}