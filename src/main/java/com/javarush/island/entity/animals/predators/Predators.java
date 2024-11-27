package com.javarush.island.entity.animals.predators;

import com.javarush.island.entity.animals.Animal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class Predators extends Animal {

    public Predators(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }

}
