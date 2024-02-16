package com.example.shiftbet.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    public String title;

    Category category;

    public Team team1;

    public Team team2;

    public double team1Coefficient;

    public double team2Coefficient;

    public double drawCoefficient;

    public LocalDate beginningDate;

    public LocalDate endDate;
}
