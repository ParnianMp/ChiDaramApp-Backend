package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.DecreasedocumenttypeDTO;
import com.example.chi_daram.entity.Decreasedocumenttype;
import org.springframework.stereotype.Component;

@Component
public class DecreasedocumenttypeMapper {

    public Decreasedocumenttype toEntity(DecreasedocumenttypeDTO dto) {
        if (dto == null) {
            return null;
        }
        Decreasedocumenttype entity = new Decreasedocumenttype();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public DecreasedocumenttypeDTO toDTO(Decreasedocumenttype entity) {
        if (entity == null) {
            return null;
        }
        DecreasedocumenttypeDTO dto = new DecreasedocumenttypeDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}