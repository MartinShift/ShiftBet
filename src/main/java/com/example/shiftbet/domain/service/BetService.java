package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.entity.Bet;
import com.example.shiftbet.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    private  final CountryRepository countryRepository;

    private final BetRepository betRepository;
    private  final  GameResultRepository gameResultRepository;
    @Autowired
    public BetService(GameRepository gameRepository, TeamRepository teamRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, CountryRepository countryRepository, BetRepository betRepository, GameResultRepository gameResultRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.countryRepository = countryRepository;
        this.betRepository = betRepository;
        this.gameResultRepository = gameResultRepository;
    }

    public void deleteAll(List<Bet> bets)
    {
        betRepository.deleteAll(bets);
    }

    public void save(Bet bet)
    {
        betRepository.save(bet);
    }

    public Bet get(long id)
    {
        return  betRepository.findById(id).orElse(null);
    }

    public void remove(Bet bet)
    {
    betRepository.delete(bet);
    }
}
