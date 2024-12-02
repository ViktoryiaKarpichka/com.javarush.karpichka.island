package com.javarush.island;

import com.javarush.island.entity.Island;
import com.javarush.island.services.Simulation;

public class IslandApp {
    public static void main(String[] args) {
        Island island = new Island();
        island.fillingLocations();

        Simulation simulation = new Simulation(island);
        simulation.run(1);
    }
}
