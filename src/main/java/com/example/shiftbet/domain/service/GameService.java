package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.repository.*;
import com.example.shiftbet.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public GameService(GameRepository gameRepository, TeamRepository teamRepository, CategoryRepository categoryRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.categoryRepository = categoryRepository;
    }

    public void initializeDatabase() {
        // Create teams
        Team team1 = new Team();
        team1.setName("Team A");
        team1.setDescription("Description of Team A");
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setName("Team B");
        team2.setDescription("Description of Team B");
        teamRepository.save(team2);

        Category category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);

        Game game = new Game();
        game.setTitle("Game 1");
        game.setTeam1(team1);
        game.setTeam2(team2);
        game.setCategory(category);
        game.setTeam1Coefficient(1.5);
        game.setTeam2Coefficient(2.0);
        game.setDrawCoefficient(3.0);
        game.setBeginningDate(LocalDateTime.now().plusDays(7));
        game.setEndDate(LocalDateTime.now().plusDays(7).plusHours(2));
        gameRepository.save(game);
    }
}
