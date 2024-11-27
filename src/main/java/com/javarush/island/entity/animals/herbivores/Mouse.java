package com.javarush.island.entity.animals.herbivores;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;

@YamlEatingProbability(key = "Mouse", value = TypeOrganism.MOUSE)
@YamlOrganism(key = "Mouse")
public class Mouse extends Herbivores {

    public Mouse(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }
}
