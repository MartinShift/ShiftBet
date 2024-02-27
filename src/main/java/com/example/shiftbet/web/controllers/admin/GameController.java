package com.example.shiftbet.web.controllers.admin;

import com.example.shiftbet.domain.entity.Category;
import com.example.shiftbet.domain.entity.Category;
import com.example.shiftbet.domain.entity.Game;
import com.example.shiftbet.domain.entity.GameResult;
import com.example.shiftbet.domain.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
@Controller
public class GameController {

    private final GameService gameService;

    private final CategoryService categoryService;
    private  final SubCategoryService subCategoryService;

    private final TeamService teamService;

    private final GameResultService gameResultService;
    public GameController(GameService gameService, CategoryService categoryService, SubCategoryService subCategoryService, TeamService teamService, GameResultService gameResultService) {
        this.gameService = gameService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.teamService = teamService;
        this.gameResultService = gameResultService;
    }

    @GetMapping("/admin/game/add")
    public String showAdd(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("subcategories",subCategoryService.getAll());
        return "admin/game/add";
    }

    @GetMapping("/admin/game/active")
    public String showAll(Model model) {
        List<Game> games = gameService.getActiveGames();
        model.addAttribute("games", games);
        return "admin/game/active";
    }
    @PostMapping("/admin/game/add")
    public String add(Game game) {
        game.setCategory(game.getSubcategory().getParentCategory());
        gameService.add(game);
        return "redirect:/admin/game/active";
    }

    @GetMapping("/admin/game/finished")
    public String viewResults(Model model)
    {
        model.addAttribute("games",gameService.getFinishedGames());
        return "/admin/game/finished";
    }

    @GetMapping("/admin/game/result/{id}")
     public String viewResult(@PathVariable("id") int id, Model model)
    {
        var result = new GameResult();
        result.setGame(gameService.get(id));
        model.addAttribute("gameResult",result);

        return "admin/game/result";
    }
    @PostMapping("/admin/game/result")
    public String submitResult(GameResult gameResult)
    {
        gameResult.setGame(gameService.get(gameResult.getGame().getId()));
        gameService.distributeBets(gameResult);
        gameResultService.add(gameResult);
        return "redirect:/admin/game/finished";
    }

    @GetMapping("/admin/game/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("game", gameService.get(id));
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("subcategories",subCategoryService.getAll());
        return "admin/game/edit";
    }

    @PostMapping("/admin/game/edit")
    public String edit(Game game) throws IOException {
        gameService.update(game.getId(),game);
        return "redirect:/admin/game/active";
    }
    @GetMapping("/admin/game/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        gameService.delete(id);
        return "redirect:/admin/game/active";
    }


}

