package com.javarush.island.entity.animals.herbivores;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;

@YamlEatingProbability(key = "Boar", value = TypeOrganism.BOAR)
@YamlOrganism(key = "Boar")
public class Boar extends Herbivores {
    public Boar(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }
}
