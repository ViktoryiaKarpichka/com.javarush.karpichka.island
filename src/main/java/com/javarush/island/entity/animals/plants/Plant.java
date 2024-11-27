package com.javarush.island.entity.animals.plants;

import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.entity.Organism;
import com.javarush.island.interfaces.Reproducible;
import lombok.EqualsAndHashCode;

@YamlOrganism(key = "Plant")
@EqualsAndHashCode(callSuper = true)
public class Plant extends Organism implements Reproducible {
    public Plant(String name, double weight, int maxCountPerCell) {
        super(name, weight, maxCountPerCell);
    }

    @Override
    public void reproduce() {

    }
}
