package com.javarush.island.configuration;

import com.javarush.island.entity.Organism;
import com.javarush.island.entity.animals.Animal;
import com.javarush.island.entity.animals.plants.Plant;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unchecked")
public class OrganismConfig {

    private final Map<String, Map<String, Object>> yamlData;


    public OrganismConfig(String yamlFileName) {
        this.yamlData = (Map<String, Map<String, Object>>) YamlLoader.loadYaml(yamlFileName);
    }

    public <T extends Organism> T createOrganism(Class<T> organismClass) {

        if (!organismClass.isAnnotationPresent(YamlOrganism.class)) {
            throw new IllegalArgumentException("Class is not annotated with @YamlOrganism: " + organismClass.getName());
        }

        YamlOrganism annotation = organismClass.getAnnotation(YamlOrganism.class);
        String key = annotation.key();

        Map<String, Object> organismData =
                organismClass.isAssignableFrom(Plant.class)
                        ? yamlData.get("plants")
                        : yamlData.get("animals");

        if (organismData == null) {
            throw new IllegalArgumentException("Section not found for " + organismClass.getSimpleName() + " in YAML data");
        }

        Map<String, Object> config = (Map<String, Object>) organismData.get(key);
        if (config == null) {
            throw new IllegalArgumentException("No configuration found for organism: " + key);
        }
        try {
            if (Plant.class.isAssignableFrom(organismClass)) {

                double weight = (double) config.get("weight");
                int maxCountPerCell = (int) config.get("maxCountPerCell");
                int quantity = ThreadLocalRandom.current().nextInt(maxCountPerCell, maxCountPerCell * 10);

                Constructor<T> constructor = organismClass.getConstructor(String.class, double.class, int.class, int.class);
                return constructor.newInstance(key, weight, maxCountPerCell, quantity);
            } else if (Animal.class.isAssignableFrom(organismClass)) {

                double weight = (double) config.get("weight");
                int maxCountPerCell = (int) config.get("maxCountPerCell");
                int maxSpeed = (int) config.get("maxSpeed");
                double maxSatiety = (double) config.get("maxSatiety");

                double actualSatiety = config.containsKey("actualSatiety")
                        ? ((Number) config.get("actualSatiety")).doubleValue()
                        : maxSatiety;

                Constructor<T> constructor = organismClass.getConstructor(
                        String.class, double.class, int.class, int.class, double.class, double.class
                );
                return constructor.newInstance(key, weight, maxCountPerCell, maxSpeed, maxSatiety, actualSatiety);
            } else {
                throw new IllegalArgumentException("Unsupported organism type: " + organismClass.getName());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating organism: " + key, e);
        }
    }
}
