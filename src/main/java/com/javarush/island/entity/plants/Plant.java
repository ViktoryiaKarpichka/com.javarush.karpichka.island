package com.javarush.island.entity.plants;

import com.javarush.island.configuration.YamlEatingProbability;
import com.javarush.island.configuration.YamlOrganism;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import com.javarush.island.interfaces.Reproducible;
import com.javarush.island.model.TypeOrganism;
import lombok.Getter;
import lombok.Setter;

@YamlOrganism(key = "Plant")
@YamlEatingProbability(key = "Plant",value = TypeOrganism.PLANT)
@Getter
@Setter
public class Plant extends Organism implements Reproducible {
    private int quantity ;

    public Plant(String name, double weight, int maxCountPerCell, int quantity) {
        super(name, weight, maxCountPerCell);
        this.quantity = quantity;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    @Override
    public void reproduce(Location currentLocation) {
        if (!currentLocation.canAddOrganism(this)) {
            return;
        }
        try {
            Plant offspring = (Plant) this.clone();
            currentLocation.addOrganism(offspring);
        } catch (CloneNotSupportedException e) {
           throw new RuntimeException();
        }
    }
}
