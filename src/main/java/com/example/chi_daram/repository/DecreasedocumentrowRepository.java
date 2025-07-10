package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Decreasedocumentrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecreasedocumentrowRepository extends JpaRepository<Decreasedocumentrow, Long> {
    // Custom queries can be added here if needed
}