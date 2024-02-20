package com.example.shiftbet.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.shiftbet.domain.service.*;
@Controller
public class GameController {

    private final GameService gameService;

    @Autowired
    public  GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String init(Model model)
    {
        //gameService.initializeDatabase();
        model.addAttribute("games",gameService.getBettableGames());

        return "index";
    }

}
