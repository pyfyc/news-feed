package com.pyfyc.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "feeds")
@Getter
@Setter
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate publishDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;
}
