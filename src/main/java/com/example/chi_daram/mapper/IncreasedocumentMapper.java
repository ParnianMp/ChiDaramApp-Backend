package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.IncreasedocumentDTO;
import com.example.chi_daram.entity.Increasedocument;
import org.springframework.stereotype.Component;

@Component
public class IncreasedocumentMapper {

    public Increasedocument toEntity(IncreasedocumentDTO dto) {
        if (dto == null) {
            return null;
        }
        Increasedocument entity = new Increasedocument();
        entity.setId(dto.getId());
        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setIncreaseDate(dto.getIncreaseDate());
        entity.setDescription(dto.getDescription());
        // IncreaseType and User entities will be set in the service layer
        return entity;
    }

    public IncreasedocumentDTO toDTO(Increasedocument entity) {
        if (entity == null) {
            return null;
        }
        IncreasedocumentDTO dto = new IncreasedocumentDTO();
        dto.setId(entity.getId());
        dto.setDocumentNumber(entity.getDocumentNumber());
        dto.setIncreaseDate(entity.getIncreaseDate());
        dto.setDescription(entity.getDescription());
        if (entity.getIncreaseType() != null) {
            dto.setIncreaseTypeId(entity.getIncreaseType().getId());
        }
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        return dto;
    }
}