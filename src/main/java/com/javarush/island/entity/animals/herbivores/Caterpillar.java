package com.javarush.island.entity.animals.herbivores;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;
import lombok.Getter;
import lombok.Setter;

@YamlEatingProbability(key = "Caterpillar", value = TypeOrganism.CATERPILLAR)
@YamlOrganism(key = "Caterpillar")
@Setter
@Getter
public class Caterpillar extends Herbivores {

    public Caterpillar(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }
}
