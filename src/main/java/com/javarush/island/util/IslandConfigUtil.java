package com.javarush.island.util;

import com.javarush.island.configuration.IslandConfig;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IslandConfigUtil {
    private static  IslandConfig islandConfig;

    public static IslandConfig getIslandConfig() {
        if (islandConfig == null) {
            synchronized (IslandConfigUtil.class) {
                if (islandConfig == null) {
                    islandConfig = new IslandConfig("island_settings.yaml");
                }
            }
        }
        return islandConfig;
    }
}
