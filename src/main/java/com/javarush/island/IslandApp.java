package com.javarush.island;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.animals.herbivores.Caterpillar;
import com.javarush.island.entity.animals.herbivores.Duck;
import com.javarush.island.entity.animals.herbivores.Rabbit;
import com.javarush.island.entity.animals.predators.Wolf;

import static com.javarush.island.model.TypeOrganism.*;
import static com.javarush.island.util.OrganismFactory.createOrganism;

public class IslandApp {
    public static void main(String[] args) {
        Location location = new Location();
        Island island = new Island();
        island.createLocations();

        // пока просто вывожу в консоль
        Wolf wolf = (Wolf) createOrganism(WOLF);
        Rabbit rabbit = (Rabbit) createOrganism(RABBIT);
        Duck duck = (Duck) createOrganism(DUCK);
        Caterpillar caterpillar = (Caterpillar) createOrganism(CATERPILLAR);


        System.out.println("Wolf: " + wolf.getName() + ", Max Satiety: " + wolf.getMaxSatiety() + ", Actual Satiety: " + wolf.getActualSatiety());
        wolf.eat(rabbit);
        System.out.println("Wolf: " + wolf.getName() + ", Max Satiety: " + wolf.getMaxSatiety() + ", Actual Satiety: " + wolf.getActualSatiety());
        System.out.println("-".repeat(100));
        System.out.println("Duck: " + duck.getName() + ", Max Satiety: " + duck.getMaxSatiety() + ", Actual Satiety: " + duck.getActualSatiety());
        duck.eat(caterpillar);
        System.out.println("Duck: " + duck.getName() + ", Max Satiety: " + duck.getMaxSatiety() + ", Actual Satiety: " + duck.getActualSatiety());
    }

}
