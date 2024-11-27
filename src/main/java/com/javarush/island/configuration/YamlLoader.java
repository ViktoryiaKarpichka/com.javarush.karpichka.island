package com.javarush.island.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlLoader {
    public static Map<String, ?> loadYaml(String fileName) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = YamlLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("YAML file not found: " + fileName);
            }
            // Return the map with unknown value types
            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error loading YAML file: " + fileName, e);
        }
    }
}
