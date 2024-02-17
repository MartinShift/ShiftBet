package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
