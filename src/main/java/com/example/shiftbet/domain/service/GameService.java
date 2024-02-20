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
public class GameService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    private  final CountryRepository countryRepository;
    @Autowired
    public GameService(GameRepository gameRepository, TeamRepository teamRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, CountryRepository countryRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
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
        game.setBettable(true);
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
        return gameRepository.findByIsBettableIsTrue();
    }

}
