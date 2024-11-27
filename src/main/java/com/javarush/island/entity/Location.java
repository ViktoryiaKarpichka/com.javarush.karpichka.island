package com.javarush.island.entity;

import com.javarush.island.entity.animals.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Location {
    private int coordinateX;
    private int coordinateY;
    private List<Organism> organisms;

    public Location(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.organisms = new ArrayList<>();
    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }

    public List<Animal> getAnimal() {
        return organisms.stream().filter(organism -> organism instanceof Animal)
                .map(organism -> (Animal) organism)
                .toList();
    }

}
