package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.entity.enums.BetTypes;
import com.example.shiftbet.domain.repository.*;
import com.example.shiftbet.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@Service
public class GameResultService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    private final GameResultRepository gameResultRepository;
    private  final CountryRepository countryRepository;
    @Autowired
    public GameResultService(GameRepository gameRepository, TeamRepository teamRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, GameResultRepository gameResultRepository, CountryRepository countryRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.gameResultRepository = gameResultRepository;
        this.countryRepository = countryRepository;
    }

    public void initializeDatabase() {

        GameResult result = new GameResult();
        result.setGame(gameRepository.findById(1L).orElse(null));
        result.setWinBet(BetTypes.TEAM2_WIN);
        gameResultRepository.save(result);

    }
    public List<GameResult> getAllGames() {
        return gameResultRepository.findAll();
    }

    public void add(GameResult result)
    {
        gameResultRepository.save(result);
    }

}
