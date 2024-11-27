package com.javarush.island.util;

import com.javarush.island.configuration.EatingProbabilityConfig;
import lombok.experimental.UtilityClass;


@UtilityClass
public class EatingProbabilityUtil {
    public static EatingProbabilityConfig eatingProbabilityConfig = new EatingProbabilityConfig("probability.yaml");
}
