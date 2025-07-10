package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Increasedocumentrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncreasedocumentrowRepository extends JpaRepository<Increasedocumentrow, Long> {
    // Custom queries can be added here if needed
}