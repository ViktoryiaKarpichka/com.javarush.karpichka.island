package com.javarush.island.model;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Statistic {
    private final Island island;

    public void printStats() {
        System.out.println("Island Statistics:");
        System.out.println("------------------");
        System.out.println("Number of animals on the island: " + countElements(Location::getAnimals));
        System.out.println("Number of plants on the island: " + countPlants());

        if (shouldTerminateSimulation()) {
            System.out.println("Simulation ends: No herbivores remain or only predators are left.");
            System.exit(0);
        }
    }

    public boolean shouldTerminateSimulation() {
        Map<String, Long> animalCounts = groupElements(Location::getAnimals);

        animalCounts.forEach((type, count) -> System.out.print(type + ": " + count + " | "));
        System.out.println();

        long herbivoresCount = animalCounts.entrySet().stream()
                .filter(entry -> isHerbivore(entry.getKey()))
                .mapToLong(Map.Entry::getValue)
                .sum();

        long predatorsCount = animalCounts.entrySet().stream()
                .filter(entry -> isPredator(entry.getKey()))
                .mapToLong(Map.Entry::getValue)
                .sum();

        System.out.println("Herbivores count: " + herbivoresCount);
        System.out.println("Predators count: " + predatorsCount);

        return herbivoresCount == 0 || (predatorsCount > 0 && herbivoresCount == 0 || countPlants() <= 0);
    }


    private long countElements(Function<Location, List<? extends Organism>> extractor) {
        return Arrays.stream(island.getLocations())
                .flatMap(Arrays::stream)
                .mapToLong(location -> extractor.apply(location).size())
                .sum();
    }

    private long countPlants() {
        return Arrays.stream(island.getLocations())
                .flatMap(Arrays::stream)
                .mapToLong(location -> location.getPlants().size())
                .sum();
    }

    private Map<String, Long> groupElements(Function<Location, List<? extends Organism>> extractor) {
        return Arrays.stream(island.getLocations())
                .flatMap(Arrays::stream)
                .flatMap(location -> extractor.apply(location).stream())
                .collect(Collectors.groupingBy(
                        organism -> organism.getClass().getSimpleName(),
                        Collectors.counting()
                ));
    }

    private boolean isHerbivore(String className) {
        return List.of("Horse", "Deer", "Rabbit", "Mouse", "Goat", "Sheep", "Boar", "Buffalo", "Duck", "Caterpillar")
                .contains(className);
    }

    private boolean isPredator(String className) {
        return List.of("Wolf", "Boa", "Bear", "Eagle", "Fox").contains(className);
    }
}

//  public void printStats() {
//        System.out.println("Island Statistics:");
//        System.out.println("------------------");
//        System.out.println("Number of animals on the island: " + countElements(Location::getAnimals));
//        System.out.println("Number of plants on the island: " + countPlants());
//
////        if (shouldTerminateSimulation()) {
////            System.out.println("Simulation ends: No herbivores remain or only predators are left.");
////            System.exit(0);
////        }
//    }
//
//    public boolean shouldTerminateSimulation() {
//        Map<String, Long> animalCounts = groupElements(Location::getAnimals);
//
//        animalCounts.forEach((type, count) -> System.out.print(type + ": " + count + " | "));
//        System.out.println();
//
//        long herbivoresCount = animalCounts.entrySet().stream()
//                .filter(entry -> isHerbivore(entry.getKey()))
//                .mapToLong(Map.Entry::getValue)
//                .sum();
//
//        long predatorsCount = animalCounts.entrySet().stream()
//                .filter(entry -> isPredator(entry.getKey()))
//                .mapToLong(Map.Entry::getValue)
//                .sum();
//
//        System.out.println("Herbivores count: " + herbivoresCount);
//        System.out.println("Predators count: " + predatorsCount);
//
//        return herbivoresCount == 0 || (predatorsCount > 0 && herbivoresCount == 0 || countPlants() <= 0);
//    }
//
//    private long countElements(Function<Location, List<? extends Organism>> extractor) {
//        return Arrays.stream(island.getLocations())
//                .flatMap(Arrays::stream)
//                .mapToLong(location -> extractor.apply(location).size())
//                .sum();
//    }
//
//    private long countPlants() {
//        return Arrays.stream(island.getLocations())
//                .flatMap(Arrays::stream)
//                .mapToLong(location -> location.getPlants().size())
//                .sum();
//    }
//
//    private Map<String, Long> groupElements(Function<Location, List<? extends Organism>> extractor) {
//        return Arrays.stream(island.getLocations())
//                .flatMap(Arrays::stream)
//                .flatMap(location -> extractor.apply(location).stream())
//                .collect(Collectors.groupingBy(
//                        organism -> organism.getClass().getSimpleName(),
//                        Collectors.counting()
//                ));
//    }
//
//    private boolean isHerbivore(String className) {
//        return List.of("Horse", "Deer", "Rabbit", "Mouse", "Goat", "Sheep", "Boar", "Buffalo", "Duck", "Caterpillar")
//                .contains(className);
//    }
//
//    private boolean isPredator(String className) {
//        return List.of("Wolf", "Boa", "Bear", "Eagle", "Fox").contains(className);
//    }
//}
