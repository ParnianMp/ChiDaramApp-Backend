package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Dynamicfields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicfieldsRepository extends JpaRepository<Dynamicfields, Long> {
    // Custom queries can be added here if needed, e.g., find by itemid
}