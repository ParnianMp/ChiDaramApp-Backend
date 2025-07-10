package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.IncreasedocumenttypeDTO;
import com.example.chi_daram.entity.Increasedocumenttype;
import org.springframework.stereotype.Component;

@Component
public class IncreasedocumenttypeMapper {

    public Increasedocumenttype toEntity(IncreasedocumenttypeDTO dto) {
        if (dto == null) {
            return null;
        }
        Increasedocumenttype entity = new Increasedocumenttype();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public IncreasedocumenttypeDTO toDTO(Increasedocumenttype entity) {
        if (entity == null) {
            return null;
        }
        IncreasedocumenttypeDTO dto = new IncreasedocumenttypeDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}