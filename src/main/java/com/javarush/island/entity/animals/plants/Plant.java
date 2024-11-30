package com.javarush.island.entity.animals.plants;

import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import com.javarush.island.interfaces.Reproducible;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@YamlOrganism(key = "Plant")
@EqualsAndHashCode(callSuper = true)
@ToString
public class Plant extends Organism implements Reproducible {
    public Plant(String name, double weight, int maxCountPerCell) {
        super(name, weight, maxCountPerCell);
    }

    @Override
    public void reproduce(Location currentLocation) {
        if (!currentLocation.canAddOrganism(this)) {
            return;
        }
        try {
            Plant offspring = (Plant) this.clone();
            System.out.println("Clone" + offspring);
            currentLocation.addOrganism(offspring);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException();
        }
    }
}
