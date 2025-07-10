package com.example.chi_daram.repository;

import com.example.chi_daram.entity.Increasedocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncreasedocumentRepository extends JpaRepository<Increasedocument, Long> {
    boolean existsByDocumentNumber(String documentNumber);
}