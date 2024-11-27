package com.javarush.island.entity.animals.herbivores;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;

@YamlOrganism(key = "Buffalo")
@YamlEatingProbability(key = "Buffalo", value = TypeOrganism.BUFFALO)
public class Buffalo extends Herbivores {

    public Buffalo(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }
}
