package com.javarush.island.services;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import com.javarush.island.entity.animals.Animal;
import com.javarush.island.entity.plants.Plant;
import com.javarush.island.model.Statistic;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class Simulation {
    private final Island island;
    private final Statistic statistic;

    public Simulation(Island island) {
        this.island = island;
        this.statistic = new Statistic(island);
    }

    public void run(int days) {
        for (int day = 1; day <= days; day++) {
            System.out.println("Day " + day + " starts:");
            statistic.printStats();
            simulateDay();
            System.out.println("Day " + day + " ends.\n");

            statistic.printStats();

            if (statistic.shouldTerminateSimulation()) {
                System.out.println("Simulation terminated due to extinction or imbalance.");
                break;
            }
        }
    }

    private void simulateDay() {
        for (int row = 0; row < island.getLengthIsland(); row++) {
            for (int column = 0; column < island.getWidthIsland(); column++) {
                Location location = island.getLocation(row, column);
                processLocation(location);
            }
        }
    }

    private void processLocation(Location location) {
        List<Organism> organisms = new ArrayList<>(location.getOrganisms());
        for (Organism organism : organisms) {
            if (organism instanceof Animal animal) {
                animal.eat(location);
                animal.reproduce(location);
                animal.move(location);
            } else if (organism instanceof Plant plant) {
                plant.reproduce(location);
            }
        }
    }
}

