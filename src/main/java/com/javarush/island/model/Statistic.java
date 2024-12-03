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
        printHerbivoresAndPredatorsCount();
        System.out.println("Plants - " + countPlants());
        printAnimalCounts();
        System.out.println("------------------");
    }

    public boolean shouldTerminateSimulation() {
        Map<String, Long> animalCounts = groupElements(Location::getAnimals);
        return getHerbivoresCount(animalCounts) == 0 || countPlants() == 0 || getPredatorsCount(animalCounts) == 0;
    }

    private void printAnimalCounts() {
        Map<String, Long> animalCounts = groupElements(Location::getAnimals);
        animalCounts.forEach((type, count) -> System.out.print(type + ": " + count + " | "));
        System.out.println();
    }

    private void printHerbivoresAndPredatorsCount() {
        Map<String, Long> animalCounts = groupElements(Location::getAnimals);

        System.out.println("Herbivores count - " + getHerbivoresCount(animalCounts));
        System.out.println("Predators count - " + getPredatorsCount(animalCounts));
    }

    private long getHerbivoresCount(Map<String, Long> animalCounts) {
        return animalCounts.entrySet().stream()
                .filter(entry -> isHerbivore(entry.getKey()))
                .mapToLong(Map.Entry::getValue)
                .sum();
    }

    private long getPredatorsCount(Map<String, Long> animalCounts) {
        return animalCounts.entrySet().stream()
                .filter(entry -> isPredator(entry.getKey()))
                .mapToLong(Map.Entry::getValue)
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
