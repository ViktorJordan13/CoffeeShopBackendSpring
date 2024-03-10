package com.codebrains.coffeeshopbackendspring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coffee")
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "coffee_doses")
    private Integer coffeeDoses;

    @Column(name = "milk_doses")
    private Integer milkDoses;

    @Column(name = "sugar_packs")
    private Integer sugarPacks;

    @Column(name = "cream")
    private Boolean cream;

    @Column(name = "selected")
    private Boolean selected;

}