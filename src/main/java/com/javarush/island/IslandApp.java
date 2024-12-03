package com.javarush.island;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.animals.herbivores.Caterpillar;
import com.javarush.island.entity.animals.herbivores.Duck;

import static com.javarush.island.model.TypeOrganism.CATERPILLAR;
import static com.javarush.island.model.TypeOrganism.DUCK;
import static com.javarush.island.util.OrganismFactory.createOrganism;

public class IslandApp {
    public static void main(String[] args) {
        Island island = new Island();
        island.createLocations();
        island.fillingLocations();
        island.debugIslandContent();

//        Simulation simulation = new Simulation(island);
//        simulation.run(1);

        island.debugIslandContent();

        Caterpillar caterpillar = (Caterpillar) createOrganism(CATERPILLAR);
        Duck duck = (Duck) createOrganism(DUCK);
        duck.eat(island.getLocation(0, 0));

    }
}
