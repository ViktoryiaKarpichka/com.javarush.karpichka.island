package com.javarush.island.entity;

import com.javarush.island.configuration.IslandConfig;
import com.javarush.island.util.IslandUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Island {
    private int widthIsland;
    private int lengthIsland;
    private Location[][] locations;

    //get config param
    public Island() {
        IslandConfig config = IslandUtil.getIslandConfig();
        this.widthIsland = config.getWidth();
        this.lengthIsland = config.getHeight();
        this.locations = new Location[lengthIsland][widthIsland];
    }

    public void createLocations() {
        for (int row = 0; row < getLengthIsland(); row++) {
            for (int column = 0; column < getWidthIsland(); column++) {
                locations[row][column] = new Location(row, column);
            }
        }
    }
}
