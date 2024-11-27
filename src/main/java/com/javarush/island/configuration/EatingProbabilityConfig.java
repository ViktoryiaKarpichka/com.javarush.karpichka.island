package com.javarush.island.configuration;

import com.javarush.island.entity.Organism;
import com.javarush.island.model.TypeOrganism;

import java.util.Map;

@SuppressWarnings("unchecked")
public class EatingProbabilityConfig {
    private static Map<String, Map<String, Integer>> eatingProbability;

    public EatingProbabilityConfig(String yamlFilePath) {
        try {
            Map<String, ?> yamlData = YamlLoader.loadYaml(yamlFilePath);
            eatingProbability = (Map<String, Map<String, Integer>>) yamlData.get("eatingProbability");
            if (eatingProbability == null) {
                throw new IllegalArgumentException("Key 'eatingProbability' not found in YAML file");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load eating probabilities from YAML", e);
        }
    }

    public static TypeOrganism getTypeOrganism(Organism organism) {
        YamlEatingProbability annotation = organism.getClass().getAnnotation(YamlEatingProbability.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Animal class is not annotated with @OrganismType: " + organism.getClass());
        }
        return annotation.value();
    }

    public static int getProbability(Organism predator, Organism prey) {
        TypeOrganism predatorType = getTypeOrganism(predator);
        TypeOrganism preyType = getTypeOrganism(prey);

        return eatingProbability
                .getOrDefault(predatorType.getName(), Map.of())
                .getOrDefault(preyType.getName(), 0);
    }

    public static boolean canEat(Organism predator, Organism prey) {
        TypeOrganism predatorType = getTypeOrganism(predator);
        TypeOrganism preyType = getTypeOrganism(prey);

        return eatingProbability.containsKey(predatorType.getName()) &&
                eatingProbability.get(predatorType.getName()).containsKey(preyType.getName());
    }
}