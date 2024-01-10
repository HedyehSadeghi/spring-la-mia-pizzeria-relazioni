package org.learning.springpizzeria.repository;

import org.learning.springpizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzeriaRepository extends JpaRepository<Pizza, String> {
    Optional<Pizza> findByName(String name);
}
