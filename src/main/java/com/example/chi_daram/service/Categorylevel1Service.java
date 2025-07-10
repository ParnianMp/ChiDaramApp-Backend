package com.example.chi_daram.service;

import com.example.chi_daram.dto.Categorylevel1DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing Categorylevel1.
 */
public interface Categorylevel1Service {

    /**
     * Creates a new Categorylevel1.
     *
     * @param dto The DTO containing the data for the new category.
     * @return The created category as a DTO.
     */
    Categorylevel1DTO createCategorylevel1(Categorylevel1DTO dto);

    /**
     * Updates an existing Categorylevel1.
     *
     * @param id  The ID of the category to update.
     * @param dto The DTO with the updated data.
     * @return The updated category as a DTO.
     */
    Categorylevel1DTO updateCategorylevel1(Long id, Categorylevel1DTO dto);

    /**
     * Deletes a Categorylevel1 by its ID.
     *
     * @param id The ID of the category to delete.
     */
    void deleteCategorylevel1(Long id);

    /**
     * Retrieves a Categorylevel1 by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The found category as a DTO.
     */
    Categorylevel1DTO getCategorylevel1ById(Long id);

    /**
     * Retrieves a paginated list of all Categorylevel1 entities.
     *
     * @param pageable The pagination information.
     * @return A page of Categorylevel1 DTOs.
     */
    Page<Categorylevel1DTO> getAllCategorylevel1s(Pageable pageable);
}
