package com.javarush.island;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.animals.herbivores.Caterpillar;
import com.javarush.island.entity.animals.herbivores.Duck;
import com.javarush.island.entity.animals.herbivores.Rabbit;
import com.javarush.island.entity.animals.plants.Plant;
import com.javarush.island.entity.animals.predators.Wolf;

import static com.javarush.island.model.TypeOrganism.*;
import static com.javarush.island.util.OrganismFactory.createOrganism;

public class IslandApp {
    public static void main(String[] args) {
        Island island = new Island();
        island.createLocations();
        island.fillingLocations();

        Wolf wolf = (Wolf) createOrganism(WOLF);
        Rabbit rabbit = (Rabbit) createOrganism(RABBIT);
        Duck duck = (Duck) createOrganism(DUCK);
        Caterpillar caterpillar = (Caterpillar) createOrganism(CATERPILLAR);
        Plant plant = (Plant) createOrganism(PLANT);


        System.out.println("Wolf: " + wolf.getName() + ", Max Satiety: " + wolf.getMaxSatiety() + ", Actual Satiety: " + wolf.getActualSatiety());
        island.getLocation(0, 0).getOrganismStatistics().forEach((organismClass, count) ->
                System.out.print(organismClass.getSimpleName() + ": " + count+"|")
        );
        System.out.println();
        wolf.eat(island.getLocation(0, 0));
        System.out.println("rabbit: " + rabbit.getName() + ", Max Satiety: " + rabbit.getMaxSatiety() + ", Actual Satiety: " + rabbit.getActualSatiety());
        rabbit.eat(island.getLocation(0, 0));
        System.out.println("rabbit: " + rabbit.getName() + ", Max Satiety: " + rabbit.getMaxSatiety() + ", Actual Satiety: " + rabbit.getActualSatiety());
        System.out.println("Wolf: " + wolf.getName() + ", Max Satiety: " + wolf.getMaxSatiety() + ", Actual Satiety: " + wolf.getActualSatiety());
        island.getLocation(0, 0).getOrganismStatistics().forEach((organismClass, count) ->
                System.out.print(organismClass.getSimpleName() + ": " + count+"|")
        );

//        Location location = new Location(0, 0);
//
//
//
//        location.addOrganism(wolf);
//        location.addOrganism(rabbit);
//        location.addOrganism(plant);
//
//        wolf.eat(location);
//        System.out.println("-".repeat(100));
//        System.out.println("Duck: " + duck.getName() + ", Max Satiety: " + duck.getMaxSatiety() + ", Actual Satiety: " + duck.getActualSatiety());
//        duck.eat(caterpillar);
//        System.out.println("Duck: " + duck.getName() + ", Max Satiety: " + duck.getMaxSatiety() + ", Actual Satiety: " + duck.getActualSatiety());

    }

}
