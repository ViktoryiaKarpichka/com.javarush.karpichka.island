package com.javarush.island.entity.animals.herbivores;


import com.javarush.island.entity.animals.Animal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Herbivores extends Animal {

    public Herbivores(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }

}
