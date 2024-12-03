package com.javarush.island.services;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import com.javarush.island.entity.animals.Animal;
import com.javarush.island.entity.plants.Plant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Task {
   private final Location location;
    private final  Organism organism;
    private final Island island;

    public void doTask() {
        if (organism instanceof Animal animal) {
            animal.eat(location);
            animal.reproduce(location);
            animal.move(location);
        } else if (organism instanceof Plant plant) {
            plant.reproduce(location);
        }
    }
}
