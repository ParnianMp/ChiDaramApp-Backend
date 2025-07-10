package com.example.chi_daram.repository;

import com.example.chi_daram.entity.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStatusRepository extends JpaRepository<ItemStatus, Long> {
    // Custom query to check if an item status with a given status name already exists
    boolean existsByStatus(String status);
}