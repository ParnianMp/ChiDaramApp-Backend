package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.Categorylevel2DTO;
//import com.example.chi_daram.entity.Categorylevel1;
import com.example.chi_daram.entity.Categorylevel2;
import org.springframework.stereotype.Component;

@Component
public class Categorylevel2Mapper {

    // Converts DTO to Entity for saving/updating
    public Categorylevel2 toEntity(Categorylevel2DTO dto) {
        if (dto == null) {
            return null;
        }
        Categorylevel2 entity = new Categorylevel2();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        
        // Categorylevel1 needs to be fetched and set separately in the service layer
        // because we only have its ID in the DTO.
        // entity.setCategorylevel1(new Categorylevel1(dto.getCategorylevel1Id())); // This would be incomplete
        return entity;
    }

    // Converts Entity to DTO for returning data
    public Categorylevel2DTO toDTO(Categorylevel2 entity) {
        if (entity == null) {
            return null;
        }
        Categorylevel2DTO dto = new Categorylevel2DTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        if (entity.getCategorylevel1() != null) {
            dto.setCategorylevel1Id(entity.getCategorylevel1().getId());
        }
        return dto;
    }
}

