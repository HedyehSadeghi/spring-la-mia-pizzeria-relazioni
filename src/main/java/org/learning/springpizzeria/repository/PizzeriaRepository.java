package org.learning.springpizzeria.repository;

import org.learning.springpizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzeriaRepository extends JpaRepository<Pizza, String> {
}
