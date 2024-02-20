package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.Game;
import com.example.shiftbet.domain.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

}
