package com.javarush.island.util;

import com.javarush.island.configuration.EatingProbabilityConfig;
import lombok.experimental.UtilityClass;


@UtilityClass
public class EatingProbabilityUtil {
    private static EatingProbabilityConfig eatingProbabilityConfig;

    public static void getEatingProbabilityConfig() {
        if (eatingProbabilityConfig == null) {
            synchronized (EatingProbabilityUtil.class) {
                if (eatingProbabilityConfig == null) {
                    eatingProbabilityConfig = new EatingProbabilityConfig("probability.yaml");
                }
            }
        }
    }
}
