package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.repository.*;
import com.example.shiftbet.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@Service
public class TeamService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    private  final CountryRepository countryRepository;

    private  final  GameResultRepository gameResultRepository;
    @Autowired
    public TeamService(GameRepository gameRepository, TeamRepository teamRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, CountryRepository countryRepository, GameResultRepository gameResultRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.countryRepository = countryRepository;
        this.gameResultRepository = gameResultRepository;
    }

    public List<Team> getTeams()
    {
        return teamRepository.findAll();
    }

    public Team getTeam(long id)
    {
        return teamRepository.findById(id).orElse(null);
    }

    public List<GameResult> getTeamGames(long id)
    {
    return gameResultRepository.findByGameTeam1IdOrGameTeam2Id(id,id);
    }

}
