package com.javarush.island.entity.animals.herbivores;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;
import lombok.Getter;
import lombok.Setter;

@YamlEatingProbability(key = "Duck", value = TypeOrganism.DUCK)
@YamlOrganism(key = "Duck")
@Setter
@Getter
public class Duck extends Herbivores {

    public Duck(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }

}
