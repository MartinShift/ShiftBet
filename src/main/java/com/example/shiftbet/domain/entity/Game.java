package com.example.shiftbet.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "team1_id")
    private Team team1;

    @OneToOne
    @JoinColumn(name = "team2_id")
    private Team team2;

    private double team1Coefficient;

    private double team2Coefficient;

    private double drawCoefficient;

    private LocalDateTime beginningDate;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Bet> bets;

    private boolean isBettable;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
    private GameResult gameResult;
}
