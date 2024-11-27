package com.javarush.island.util;

import com.javarush.island.configuration.EatingProbabilityConfig;
import lombok.experimental.UtilityClass;


@UtilityClass
public class EatingProbabilityUtil {
    public static EatingProbabilityConfig eatingProbabilityConfig;

    public static EatingProbabilityConfig getEatingProbabilityConfig() {
        if (eatingProbabilityConfig == null) {
            synchronized (EatingProbabilityUtil.class) {
                if (eatingProbabilityConfig == null) {
                    eatingProbabilityConfig = new EatingProbabilityConfig("probability.yaml");
                }
            }
        }
        return eatingProbabilityConfig;
    }

}
