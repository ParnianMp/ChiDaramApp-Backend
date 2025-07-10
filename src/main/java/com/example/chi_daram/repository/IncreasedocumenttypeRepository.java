package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Increasedocumenttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncreasedocumenttypeRepository extends JpaRepository<Increasedocumenttype, Long> {
    boolean existsByTitle(String title);
}