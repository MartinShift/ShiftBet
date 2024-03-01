package com.example.shiftbet.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.shiftbet.domain.service.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Controller
public class MainController {

    private final GameService gameService;
    private final CategoryService categoryService;
    private  final SubCategoryService subCategoryService;
    private final TeamService teamService;
    @Autowired
    public  MainController(GameService gameService, CategoryService categoryService, SubCategoryService subCategoryService, TeamService teamService) {
        this.gameService = gameService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.teamService = teamService;
    }

    @GetMapping("/")
     public String mainPage()
    {
        //gameService.initializeDatabase();
        return"redirect:/main/view";
    }

    @GetMapping("/main/view")
    public String index(Model model)
    {
        model.addAttribute("games",gameService.getActiveGames());
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("subcategories",subCategoryService.getTopSubcategories());
        return "/main/index";
    }
    @GetMapping("/main/category/{categoryId}")
    public String viewCategoryGames(Model model, @PathVariable long categoryId)
    {
        model.addAttribute("games", gameService.getCategoryGames(categoryId ));
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("subcategories",subCategoryService.getTopSubcategories());
        return "/main/index";
    }
    @GetMapping("/main/subcategory/{subcategoryId}")
    public String viewSubcategoryGames(Model model, @PathVariable long subcategoryId)
    {
        model.addAttribute("games", gameService.getSubcategoryGames(subcategoryId));
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("subcategories",subCategoryService.getTopSubcategories());
        return "/main/index";
    }

    @GetMapping("/main/team-view/{id}")
    public String viewTeam(Model model, @PathVariable long id)
    {
        model.addAttribute("team",teamService.getTeam(id));
        model.addAttribute("teamResults",teamService.getTeamGames(id));
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("subcategories",subCategoryService.getTopSubcategories());
        return "main/team-view";
    }

    @GetMapping("/main/live")
    public String live(Model model)
    {
        model.addAttribute("games",gameService.getActiveGames().stream().filter(s-> s.getBeginningDate().isBefore(LocalDateTime.now()) && s.isBettable()).toList());
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("subcategories",subCategoryService.getTopSubcategories());
        return "/main/index";
    }
    @GetMapping("/main/next")
    public String next(Model model)
    {
        model.addAttribute("games",gameService.getActiveGames().stream().filter(s-> s.getBeginningDate().isAfter(LocalDateTime.now())).toList());
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("subcategories",subCategoryService.getTopSubcategories());
        return "/main/index";
    }
}
