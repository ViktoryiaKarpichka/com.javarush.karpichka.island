package com.javarush.island.entity.animals.predators;


import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.model.TypeOrganism;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@YamlOrganism(key = "Boa")
@YamlEatingProbability(key = "Boa", value = TypeOrganism.BOA)
public class Boa extends Predators {
    public Boa(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
    }
}
