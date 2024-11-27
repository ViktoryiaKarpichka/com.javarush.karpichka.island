package com.javarush.island.entity.animals.predators;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;
import lombok.Getter;
import lombok.Setter;

@YamlEatingProbability(key = "Wolf", value = TypeOrganism.WOLF)
@YamlOrganism(key = "Wolf")
@Setter
@Getter
public class Wolf extends Predators {
    public Wolf(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }

}
