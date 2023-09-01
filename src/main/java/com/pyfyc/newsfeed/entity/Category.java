package com.pyfyc.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Feed> news;
}
