package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Custom query to check if an item with a given name already exists under a specific category.
    // This will require a method signature like:
    // boolean existsByNameAndCategorylevel2(String name, Categorylevel2 categorylevel2);
    // For simplicity, we'll start with a global name check if needed, but the above is more precise.
    boolean existsByName(String name);
}