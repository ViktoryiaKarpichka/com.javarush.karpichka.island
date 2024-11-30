package com.javarush.island.entity;

import com.javarush.island.entity.animals.Animal;
import com.javarush.island.entity.animals.plants.Plant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Setter
@Getter
@ToString
public class Location {
    private int coordinateX;
    private int coordinateY;
    private final List<Organism> organisms = new CopyOnWriteArrayList<>();

    public Location(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }
    public Location getCurrentLocationOfOrganism(Organism organism) {
        if (organisms.contains(organism)) {
            return this;
        }
        return null;
    }

    public List<Animal> getAnimals() {
        return organisms.stream()
                .filter(organism -> organism instanceof Animal)
                .map(organism -> (Animal) organism).toList();
    }

    public List<Plant> getPlants() {
        return organisms.stream()
                .filter(organism -> organism instanceof Plant)
                .map(organism -> (Plant) organism).toList();
    }

    public boolean canAddOrganism(Organism organism) {
        long sameTypeCount = organisms.stream()
                .filter(o -> o.getClass().equals(organism.getClass()))
                .count();
        return sameTypeCount < organism.getMaxCountPerCell();
    }
}
