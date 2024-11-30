package com.javarush.island.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class Organism {
    private String name;
    private double weight;
    private int maxCountPerCell;

    // умирать от голода или быть съеденными.
    void die() {

    }

}
