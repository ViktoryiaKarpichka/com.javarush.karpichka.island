package com.javarush.island.util;

import com.javarush.island.configuration.OrganismConfig;
import com.javarush.island.entity.Organism;
import com.javarush.island.entity.animals.herbivores.*;
import com.javarush.island.entity.animals.plants.Plant;
import com.javarush.island.entity.animals.predators.*;
import com.javarush.island.model.TypeOrganism;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrganismFactory {
    public static OrganismConfig animalConfig = new OrganismConfig("organisms.yaml");

    public static Organism createOrganism(TypeOrganism type) {

       return switch (type) {
            case BEAR -> animalConfig.createOrganism(Bear.class);
            case EAGLE -> animalConfig.createOrganism(Eagle.class);
            case FOX -> animalConfig.createOrganism(Fox.class);
            case BOA -> animalConfig.createOrganism(Boa.class);
            case WOLF -> animalConfig.createOrganism(Wolf.class);
            case BUFFALO -> animalConfig.createOrganism(Buffalo.class);
            case CATERPILLAR -> animalConfig.createOrganism(Caterpillar.class);
            case DEER -> animalConfig.createOrganism(Deer.class);
            case DUCK -> animalConfig.createOrganism(Duck.class);
            case GOAT -> animalConfig.createOrganism(Goat.class);
            case BOAR -> animalConfig.createOrganism(Boar.class);
            case HORSE -> animalConfig.createOrganism(Horse.class);
            case MOUSE -> animalConfig.createOrganism(Mouse.class);
            case RABBIT -> animalConfig.createOrganism(Rabbit.class);
            case SHEEP -> animalConfig.createOrganism(Sheep.class);
            case PLANT ->animalConfig.createOrganism(Plant.class); 
       };
    }
}

