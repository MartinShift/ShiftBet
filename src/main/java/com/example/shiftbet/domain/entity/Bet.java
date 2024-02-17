package com.example.shiftbet.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bet {
    public enum BetTypes
    {
        TEAM1_WIN, TEAM2_WIN, DRAW,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    private BetTypes betType;

    //private User user;
}
