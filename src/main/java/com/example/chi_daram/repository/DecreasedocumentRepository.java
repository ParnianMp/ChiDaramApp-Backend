package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Decreasedocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecreasedocumentRepository extends JpaRepository<Decreasedocument, Long> {
    boolean existsByDocumentNumber(String documentNumber);
}