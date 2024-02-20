package com.example.shiftbet.web.controllers;

import com.example.shiftbet.domain.service.GameService;
import com.example.shiftbet.domain.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.shiftbet.domain.entity.enums.BetTypes;
@Controller
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public  TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/team/view")
    public String viewTeam(Model model, @RequestParam long id)
    {
        model.addAttribute("team",teamService.getTeam(id));
        model.addAttribute("teamResults",teamService.getTeamGames(id));

        return "team-view";
    }
}
