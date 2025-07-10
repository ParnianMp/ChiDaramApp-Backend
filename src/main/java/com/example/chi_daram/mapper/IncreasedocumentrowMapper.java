package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.IncreasedocumentrowDTO;
import com.example.chi_daram.entity.Increasedocumentrow;
import org.springframework.stereotype.Component;

@Component
public class IncreasedocumentrowMapper {

    public Increasedocumentrow toEntity(IncreasedocumentrowDTO dto) {
        if (dto == null) {
            return null;
        }
        Increasedocumentrow entity = new Increasedocumentrow();
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setDescription(dto.getDescription());
        // Increasedocument and Item entities will be set in the service layer
        return entity;
    }

    public IncreasedocumentrowDTO toDTO(Increasedocumentrow entity) {
        if (entity == null) {
            return null;
        }
        IncreasedocumentrowDTO dto = new IncreasedocumentrowDTO();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setDescription(entity.getDescription());
        if (entity.getIncreasedocument() != null) {
            dto.setIncreasedocumentId(entity.getIncreasedocument().getId());
        }
        if (entity.getItem() != null) {
            dto.setItemId(entity.getItem().getId());
        }
        return dto;
    }
}