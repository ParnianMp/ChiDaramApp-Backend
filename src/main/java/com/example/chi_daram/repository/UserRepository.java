package com.example.chi_daram.repository;

import com.example.chi_daram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber); 
    boolean existsByUsername(String username); 
}