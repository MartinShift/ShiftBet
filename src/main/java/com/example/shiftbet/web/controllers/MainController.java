package com.example.shiftbet.web.controllers;

import com.example.shiftbet.domain.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.shiftbet.domain.service.*;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    private final GameService gameService;

    private final TeamService teamService;
    @Autowired
    public  MainController(GameService gameService, TeamService teamService) {
        this.gameService = gameService;
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String init(Model model)
    {
        //gameService.initializeDatabase();
        model.addAttribute("games",gameService.getBettableGames());

        return "index";
    }
    @GetMapping("/team-view/{id}")
    public String viewTeam(Model model, @PathVariable long id)
    {
        model.addAttribute("team",teamService.getTeam(id));
        model.addAttribute("teamResults",teamService.getTeamGames(id));

        return "team-view";
    }
}
