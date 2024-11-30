package com.javarush.island.util;

import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import lombok.experimental.UtilityClass;


@UtilityClass
public class LocationUtil {

    public static boolean canAddOrganism(Location location, Organism organism) {
        return location.getOrganisms().size() < organism.getMaxCountPerCell();
    }

}

