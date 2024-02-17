package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Long> {
}
