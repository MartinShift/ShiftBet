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
    private final  UserRepository userRepository;
    private final BetRepository betRepository;
    private final CountryRepository countryRepository;
    
    private final EmailService emailService;
    @Autowired
    public GameService(GameRepository gameRepository, TeamRepository teamRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, UserRepository userRepository, BetRepository betRepository, CountryRepository countryRepository, EmailService emailService) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.userRepository = userRepository;
        this.betRepository = betRepository;
        this.countryRepository = countryRepository;
        this.emailService = emailService;
    }

    public void finish(long  id)
    {
    Game game = gameRepository.findById(id).orElse(null);
    game.setEndDate(LocalDateTime.now().minusSeconds(1));
    gameRepository.save(game);
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
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
        gameRepository.delete(game);
    }

    public void distributeBets(GameResult result)
    {
        Game game = result.getGame();
        List<Bet> bets = result.getGame().getBets();
        List<Bet> winBets = bets.stream().filter(b-> b.getBetType() == result.getWinBet()).toList();
        double winCoefficient = 0;
        switch (result.getWinBet())
        {
            case TEAM1_WIN -> winCoefficient = game.getTeam1Coefficient();
            case DRAW -> winCoefficient = game.getDrawCoefficient();
            case TEAM2_WIN -> winCoefficient = game.getTeam2Coefficient();
        }

        double finalWinCoefficient = winCoefficient;
        winBets.forEach(b-> b.getUser().addBalance(b.getAmount() * finalWinCoefficient));
        bets.forEach(b->
                {
                    b.setEnded(true);
                    StringBuilder sb = new StringBuilder();
                    sb.append("You" + (b.isWin() ?  "won " + (b.getAmount() * finalWinCoefficient) + " USD"  : "lost") + " bet on");
                    sb.append("\"" + game.getTeam1() + " vs " + game.getTeam2() + "\" ");
                    sb.append("with amount " + b.getAmount() + " USD\n");
                    emailService.send(b.getUser().getUsername(),"Bet Result",sb.toString());
                }
        );
        betRepository.saveAll(bets);
        List<User> users = bets.stream().map(Bet::getUser).distinct().toList();

        userRepository.saveAll(users);
        
        
    }
    public List<Game> getCategoryGames(long categoryId)
    {
        return getActiveGames().stream().filter(g-> g.getCategory().getId() == categoryId).toList();
    }
    public List<Game> getSubcategoryGames(long subcategoryId)
    {
        return getActiveGames().stream().filter(g-> g.getSubcategory().getId() == subcategoryId).toList();
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
