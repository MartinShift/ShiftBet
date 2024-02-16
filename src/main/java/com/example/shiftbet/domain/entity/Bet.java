package com.example.shiftbet.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bet {
    public enum BetTypes
    {
        TEAM1_WIN, TEAM2_WIN, DRAW,
    }

    private int Id;

    private double amount;

    private Match match;

    private BetTypes betType;

    //private User user;
}
