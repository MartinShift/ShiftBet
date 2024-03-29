package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.Country;
import com.example.shiftbet.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
