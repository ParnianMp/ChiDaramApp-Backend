package com.example.chi_daram.repository;

import com.example.chi_daram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username); // این متد نیاز داریم
}
/*package com.example.chi_daram.repository;

import com.example.chi_daram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to check if a user with a given title already exists
    boolean existsByTitle(String title);
}
*/