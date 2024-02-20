package com.example.shiftbet.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private int wins;

    private int loses;

    private int draws;

    private String logoUrl;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
