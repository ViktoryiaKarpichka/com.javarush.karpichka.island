package com.javarush.island.entity.animals.predators;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;

@YamlOrganism(key = "Bear")
@YamlEatingProbability(key = "Bear", value = TypeOrganism.BEAR)
public class Bear extends Predators {
    public Bear(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }
}
