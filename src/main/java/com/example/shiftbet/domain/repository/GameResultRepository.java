package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.Game;
import com.example.shiftbet.domain.entity.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
    List<GameResult> findByGameTeam1IdOrGameTeam2Id(long teamId1, long teamId2);
}
