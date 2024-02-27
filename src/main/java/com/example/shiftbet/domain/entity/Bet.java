package com.example.shiftbet.domain.entity;

import com.example.shiftbet.domain.entity.enums.BetTypes;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    private BetTypes betType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean ended;

    public boolean isWin()
    {
        if(ended)
        {
            return betType == game.getGameResult().getWinBet();
        }
        return false;
    }

}
