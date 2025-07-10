package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Categorylevel2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Categorylevel2Repository extends JpaRepository<Categorylevel2, Long> {
    // Custom queries can be added here if needed, e.g., find by name
    boolean existsByName(String name);
}