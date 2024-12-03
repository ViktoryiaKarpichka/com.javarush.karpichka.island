package com.javarush.island.entity;

import com.javarush.island.configuration.IslandConfig;
import com.javarush.island.model.Direction;
import com.javarush.island.model.TypeOrganism;
import com.javarush.island.util.IslandUtil;
import com.javarush.island.util.OrganismFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Setter
@Getter
public class Island {
    private final int widthIsland;
    private final int lengthIsland;
    private final Location[][] locations;

    public Island() {
        IslandConfig config = IslandUtil.getIslandConfig();
        this.widthIsland = config.getWidth();
        this.lengthIsland = config.getHeight();
        this.locations = new Location[lengthIsland][widthIsland];
        createLocations();
        linkNeighbors();
    }

    public void createLocations() {
        for (int row = 0; row < lengthIsland; row++) {
            for (int column = 0; column < widthIsland; column++) {
                locations[row][column] = new Location(row, column);
            }
        }
    }

    public void linkNeighbors() {
        for (int row = 0; row < lengthIsland; row++) {
            for (int column = 0; column < widthIsland; column++) {
                Location current = locations[row][column];
                if (row > 0) current.setNeighbor(Direction.UP, locations[row - 1][column]);
                if (row < lengthIsland - 1) current.setNeighbor(Direction.DOWN, locations[row + 1][column]);
                if (column > 0) current.setNeighbor(Direction.LEFT, locations[row][column - 1]);
                if (column < widthIsland - 1) current.setNeighbor(Direction.RIGHT, locations[row][column + 1]);
            }
        }
    }

    public void fillingLocations() {
        for (int row = 0; row < lengthIsland; row++) {
            for (int column = 0; column < widthIsland; column++) {
                StringBuilder typeOrganism = new StringBuilder();

                for (TypeOrganism type : TypeOrganism.values()) {
                    Organism sampleOrganism = OrganismFactory.createOrganism(type);
                    int randomCount = ThreadLocalRandom.current().nextInt(1, sampleOrganism.getMaxCountPerCell() + 1);

                    for (int i = 0; i < randomCount; i++) {
                        try {
                            locations[row][column].addOrganism((Organism) sampleOrganism.clone());
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException(e);
                        }
                    }
//                    typeOrganism.append(sampleOrganism.getClass().getSimpleName())
//                            .append("-")
//                            .append(randomCount)
//                            .append(" ");
                }
//                System.out.print("[ " + typeOrganism + "]" + "\n");
            }
//            System.out.println();
        }
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < widthIsland && y >= 0 && y < lengthIsland) {
            return locations[y][x];
        }
        throw new IllegalArgumentException("Invalid coordinates: x=" + x + ", y=" + y);
    }
}
