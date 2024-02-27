package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.Game;
import com.example.shiftbet.domain.entity.Subcategory;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    @Query("SELECT s FROM Subcategory s ORDER BY SIZE(s.games) DESC")
    List<Subcategory> findTopSubcategories();

}
