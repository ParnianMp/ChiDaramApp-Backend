package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.Categorylevel1DTO;
import com.example.chi_daram.entity.Categorylevel1;
import org.springframework.stereotype.Component;

@Component
public class Categorylevel1Mapper {

    /**
     * Converts a Categorylevel1 entity to a Categorylevel1DTO.
     * @param entity The entity to convert.
     * @return The resulting DTO.
     */
    public Categorylevel1DTO toDTO(Categorylevel1 entity) {
        if (entity == null) {
            return null;
        }
        Categorylevel1DTO dto = new Categorylevel1DTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    /**
     * Converts a Categorylevel1DTO to a Categorylevel1 entity.
     * @param dto The DTO to convert.
     * @return The resulting entity.
     */
    public Categorylevel1 toEntity(Categorylevel1DTO dto) {
        if (dto == null) {
            return null;
        }
        // The class name was corrected here (Categorylevel1 instead of CategoryLevel1)
        Categorylevel1 entity = new Categorylevel1();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}