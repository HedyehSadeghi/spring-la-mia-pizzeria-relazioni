package org.learning.springpizzeria.repository;

import org.learning.springpizzeria.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
