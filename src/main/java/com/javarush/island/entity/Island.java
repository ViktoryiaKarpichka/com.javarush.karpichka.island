package com.javarush.island.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Island {
    private int wightIsland;
    private int lengthIsland;
    private Location[][] locations;

    public Island(int wightIsland, int lengthIsland, Location[][] locations) {
        this.wightIsland = wightIsland;
        this.lengthIsland = lengthIsland;
        this.locations = locations;
    }

}
