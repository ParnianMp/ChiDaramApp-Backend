package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Decreasedocumenttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecreasedocumenttypeRepository extends JpaRepository<Decreasedocumenttype, Long> {
    boolean existsByTitle(String title);
}