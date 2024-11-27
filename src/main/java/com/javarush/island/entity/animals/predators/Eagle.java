package com.javarush.island.entity.animals.predators;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;

@YamlEatingProbability(key = "Eagle", value = TypeOrganism.EAGLE)
@YamlOrganism(key = "Eagle")
public class Eagle extends Predators {
    public Eagle(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }
}
