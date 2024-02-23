package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g WHERE CURRENT_DATE BETWEEN g.beginningDate AND g.endDate")
    List<Game> findIsBetweenBeginningAndEnd();
}
