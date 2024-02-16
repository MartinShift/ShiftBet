package com.example.shiftbet.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    public String name;

    public String description;

    public int wins;

    public int loses;

    public int draws;

    public String logoUrl;
}
