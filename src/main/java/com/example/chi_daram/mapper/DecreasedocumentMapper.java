package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.DecreasedocumentDTO;
import com.example.chi_daram.entity.Decreasedocument;
import org.springframework.stereotype.Component;

@Component
public class DecreasedocumentMapper {

    public Decreasedocument toEntity(DecreasedocumentDTO dto) {
        if (dto == null) {
            return null;
        }
        Decreasedocument entity = new Decreasedocument();
        entity.setId(dto.getId());
        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setDecreaseDate(dto.getDecreaseDate());
        entity.setDescription(dto.getDescription());
        // DecreaseType and User entities will be set in the service layer
        return entity;
    }

    public DecreasedocumentDTO toDTO(Decreasedocument entity) {
        if (entity == null) {
            return null;
        }
        DecreasedocumentDTO dto = new DecreasedocumentDTO();
        dto.setId(entity.getId());
        dto.setDocumentNumber(entity.getDocumentNumber());
        dto.setDecreaseDate(entity.getDecreaseDate());
        dto.setDescription(entity.getDescription());
        if (entity.getDecreaseType() != null) {
            dto.setDecreaseTypeId(entity.getDecreaseType().getId());
        }
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        return dto;
    }
}