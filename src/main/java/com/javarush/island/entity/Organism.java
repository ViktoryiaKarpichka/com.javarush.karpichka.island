package com.javarush.island.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class Organism implements Cloneable{
    private String name;
    private double weight;
    private int maxCountPerCell;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
