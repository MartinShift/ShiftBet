package com.example.shiftbet.web.controllers;

import com.example.shiftbet.domain.aspect.Loggable;
import com.example.shiftbet.domain.entity.Bet;
import com.example.shiftbet.domain.entity.User;
import com.example.shiftbet.domain.entity.enums.BetTypes;
import com.example.shiftbet.domain.liqpay.LiqPay;
import com.example.shiftbet.domain.liqpay.Notify;
import com.example.shiftbet.domain.liqpay.Params;
import com.example.shiftbet.domain.service.BetService;
import com.example.shiftbet.domain.service.GameService;
import com.example.shiftbet.domain.service.UserService;
import com.example.shiftbet.web.dto.BetRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final BetService betService;
    private final GameService gameService;

    @Autowired
    public UserController(UserService userService, BetService betService, GameService gameService) {
        this.userService = userService;
        this.betService = betService;
        this.gameService = gameService;
    }

    @GetMapping("/user/betHistory")
    public String betHistory(Model model)
    {
        User user = userService.loadUser();
        model.addAttribute("user",user);
        model.addAttribute("betHistory",user.getBets().stream().filter(b-> b.isEnded()).toList());

        return "/user/betHistory";
    }

    @GetMapping("/user/activeBets")
    public String activeBets(Model model)
    {
        User user = userService.loadUser();
        model.addAttribute("user",user);
        model.addAttribute("activeBets",user.getBets().stream().filter(b-> !b.isEnded()).toList());
        return "/user/activeBets";
    }
    @PostMapping("/user/submitBet")
    @Loggable
    @ResponseBody
    public ResponseEntity<Object> submitBet(@RequestBody BetRequest betRequest) {
        User user = userService.loadUser();
        double doubleAmount = Double.parseDouble(betRequest.amount);
        if(user.getBalance() < doubleAmount) {
            return ResponseEntity.ok().body("{\"message\": \"Not enough balance!\"}");
        }
        user.addBalance(doubleAmount * -1);
        Bet bet = new Bet();
        bet.setGame(gameService.get(betRequest.gameId));
        bet.setAmount(doubleAmount);
        bet.setUser(user);
        bet.setEnded(false);
        bet.setBetType(BetTypes.valueOf(betRequest.betType));
        betService.save(bet);
        return ResponseEntity.ok().body("{\"message\": \"Success!\"}");
    }

    @PostMapping("/user/declineBet")
    public ResponseEntity<String> declineBet(@RequestParam("betId") int betId) {

        User user = userService.loadUser();
        Bet bet = betService.get(betId);
        user.addBalance(bet.getAmount());
        betService.remove(bet);
        return ResponseEntity.ok().body("{\"message\": \"Bet Declined Successfully!\"}");

    }

    @GetMapping("/user/topup")
    public String topUpForm(@RequestParam("amount") double amount, Model model) throws JsonProcessingException {
        LiqPay liqpay = new LiqPay();
        Params param = liqpay.payParams(amount);
        model.addAttribute("liqpayData",liqpay.getData(param));
        model.addAttribute("liqpaySignature",liqpay.getSignature(param));
        model.addAttribute("liqpayAmount",amount);
        return "/user/checkout";

    }

    @PostMapping("/user/submittopup")
    public String topUp(@RequestBody Notify notify) {

        User user = userService.loadUser();
        user.addBalance(notify.getAmount());

        userService.save(user);
        //TODO email user

        return "redirect:/main/team-view/2";

    }

}
