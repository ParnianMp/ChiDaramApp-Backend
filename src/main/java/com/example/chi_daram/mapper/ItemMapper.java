package com.example.chi_daram.mapper;

import com.example.chi_daram.dto.ItemDTO;
import com.example.chi_daram.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toEntity(ItemDTO dto) {
        if (dto == null) {
            return null;
        }
        Item entity = new Item();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setColor(dto.getColor());
        entity.setSize(dto.getSize());
        entity.setImage(dto.getImage());
        // categorylevel2 will be set in the service layer after fetching the Categorylevel2 entity
        return entity;
    }

    public ItemDTO toDTO(Item entity) {
        if (entity == null) {
            return null;
        }
        ItemDTO dto = new ItemDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setQuantity(entity.getQuantity());
        dto.setColor(entity.getColor());
        dto.setSize(entity.getSize());
        dto.setImage(entity.getImage());
        if (entity.getCategorylevel2() != null) {
            dto.setCategorylevel2Id(entity.getCategorylevel2().getId());
        }
        return dto;
    }
}