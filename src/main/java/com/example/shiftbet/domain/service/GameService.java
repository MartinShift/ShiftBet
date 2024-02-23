package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.repository.*;
import com.example.shiftbet.domain.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    private final BetRepository betRepository;
    private final CountryRepository countryRepository;
    @Autowired
    public GameService(GameRepository gameRepository, TeamRepository teamRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, BetRepository betRepository, CountryRepository countryRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.betRepository = betRepository;
        this.countryRepository = countryRepository;
    }

    public void initializeDatabase() {

        Country ukraine = new Country();
        ukraine.setName("Ukraine");
        ukraine.setFlagUrl("https://flagpoles.co.uk/wp-content/uploads/2019/06/Ukraine.png");
        countryRepository.save(ukraine);

        Country denmark = new Country();
        denmark.setName("Denmark");
        denmark.setFlagUrl("https://www.flagcolorcodes.com/data/flag-of-denmark.png");
        countryRepository.save(denmark);

        Team team1 = new Team();
        team1.setName("Navi");
        team1.setDescription("Description of Team A");
        team1.setLogoUrl("/images/navi.png");
        team1.setCountry(ukraine);
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setName("Astralis");
        team2.setDescription("Description of Team B");
        team2.setLogoUrl("/images/astralis.png");
        team2.setCountry(denmark);
        teamRepository.save(team2);

        Category category = new Category();
        category.setName("Category 1");
        category.setImageUrl("/");
        categoryRepository.save(category);

        Subcategory subcategory = new Subcategory();
        subcategory.setName("Counter Strike 2");
        subcategory.setParentCategory(category);
        subcategory.setImageUrl("/");
        subcategoryRepository.save(subcategory);


        Game game = new Game();
        game.setTitle("IEM Katowice 2020 1/2 final");
        game.setTeam1(team1);
        game.setTeam2(team2);
        game.setCategory(category);
        game.setSubcategory(subcategory);
        game.setTeam1Coefficient(1.5);
        game.setTeam2Coefficient(2.0);
        game.setDrawCoefficient(3.0);
        game.setBeginningDate(LocalDateTime.now().plusDays(7));
        game.setEndDate(LocalDateTime.now().plusDays(7).plusHours(2));
        gameRepository.save(game);

    }
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getBettableGames()
    {
        return gameRepository.findIsBetweenBeginningAndEnd();
    }
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    public Game get(long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
    }

    public void add(Game  category) {
        gameRepository.save(category);
    }
    public void update(long id, Game category) {
        gameRepository.save(category);
    }
    public void delete(long id) {
        Game category = gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
        gameRepository.delete(category);
    }

    public void distributeBets(GameResult result)
    {
        Game game = result.getGame();
        List<Bet> bets = result.getGame().getBets().stream().filter(s-> s.getBetType() == result.getWinBet()).toList();
        double winCoefficient = 0;
        switch (result.getWinBet())
        {
            case TEAM1_WIN -> winCoefficient = game.getTeam1Coefficient();
            case DRAW -> winCoefficient = game.getDrawCoefficient();
            case TEAM2_WIN -> winCoefficient = game.getTeam2Coefficient();
        }

        //bets.forEach(b-> b.better.balance += b.amount * result.winCoefficient);
        betRepository.deleteAll(bets);
    }
    public List<Game> getActiveGames()
    {
        return getAll().stream().filter(g-> g.getEndDate().isAfter(LocalDateTime.now())).toList();
    }
    public List<Game> getFinishedGames()
    {
        return getAll().stream().filter(g-> g.getEndDate().isBefore(LocalDateTime.now()) && g.getGameResult() == null).toList();
    }
}
