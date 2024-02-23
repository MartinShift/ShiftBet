package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.repository.*;
import com.example.shiftbet.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    private  final  GameResultRepository gameResultRepository;
    @Autowired
    public TeamService(TeamRepository teamRepository, GameResultRepository gameResultRepository) {
        this.teamRepository = teamRepository;
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

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    public Team get(long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team not found with id: " + id));
    }

    public void add(Team  team) {
        teamRepository.save(team);
    }
    public void update(long id, Team team) {
        teamRepository.save(team);
    }
    public void delete(long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Team not found with id: " + id));
        teamRepository.delete(team);
    }

}
