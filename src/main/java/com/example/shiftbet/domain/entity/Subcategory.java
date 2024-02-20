    package com.example.shiftbet.domain.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.*;

    import java.util.List;

    @Entity
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Subcategory {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String name;

        private String imageUrl;

        @ManyToOne
        @JoinColumn(name = "parentcategory_id")
        private Category parentCategory;

        @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
        private List<Game> games;
    }