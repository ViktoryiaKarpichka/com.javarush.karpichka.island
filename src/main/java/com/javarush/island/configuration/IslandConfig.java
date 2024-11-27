package com.javarush.island.configuration;

import lombok.Getter;

import java.util.Map;
@Getter
@SuppressWarnings("unchecked")
public class IslandConfig {
    private final int height;
    private final int width;

    public IslandConfig(String yamlFilePath) {
        try {
            Map<String, ?> yamlData = YamlLoader.loadYaml(yamlFilePath);
            Map<String, Integer> islandSettings = (Map<String, Integer>) yamlData.get("IslandSettings");
            if (islandSettings == null) {
                throw new IllegalArgumentException("Key 'IslandSettings' not found in YAML file");
            }
            this.height = islandSettings.getOrDefault("height", 0);
            this.width = islandSettings.getOrDefault("width", 0);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load island settings from YAML", e);
        }
    }
}
