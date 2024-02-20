package com.example.shiftbet.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String flagUrl;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Team> teams;
}
