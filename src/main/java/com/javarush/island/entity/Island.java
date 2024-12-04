package com.javarush.island.entity;

import com.javarush.island.configuration.IslandConfig;
import com.javarush.island.model.Direction;
import com.javarush.island.model.TypeOrganism;
import com.javarush.island.util.IslandConfigUtil;
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
        IslandConfig config = IslandConfigUtil.getIslandConfig();
        this.widthIsland = config.getWidth();
        this.lengthIsland = config.getHeight();
        this.locations = new Location[lengthIsland][widthIsland];
        createLocations();
        linkNeighbors();
    }

    public void createLocations() {
        for (int row = 0; row < lengthIsland; row++) {
            int rowLength = ThreadLocalRandom.current().nextInt(1, widthIsland + 1); // Задаем случайную длину строки
            locations[row] = new Location[rowLength];
            for (int column = 0; column < rowLength; column++) {
                locations[row][column] = new Location(row, column);
            }
        }
    }

    public void linkNeighbors() {
        for (int row = 0; row < locations.length; row++) {
            for (int column = 0; column < locations[row].length; column++) {
                Location current = locations[row][column];
                if (row > 0 && column < locations[row - 1].length) {
                    current.setNeighbor(Direction.UP, locations[row - 1][column]);
                }
                if (row < locations.length - 1 && column < locations[row + 1].length) {
                    current.setNeighbor(Direction.DOWN, locations[row + 1][column]);
                }
                if (column > 0) {
                    current.setNeighbor(Direction.LEFT, locations[row][column - 1]);
                }
                if (column < locations[row].length - 1) {
                    current.setNeighbor(Direction.RIGHT, locations[row][column + 1]);
                }
            }
        }
    }

    public void fillingLocations() {
        for (int row = 0; row < locations.length; row++) {
            if (locations[row] == null) continue;
            for (int column = 0; column < locations[row].length; column++) {
                if (locations[row][column] == null) continue;
                for (TypeOrganism type : TypeOrganism.values()) {
                    boolean skipThisType = ThreadLocalRandom.current().nextDouble() < 0.3; // 30% шанс пропустить вид
                    if (skipThisType && type != TypeOrganism.PLANT) {
                        continue;
                    }
                    Organism sampleOrganism = OrganismFactory.createOrganism(type);
                    int randomCount = ThreadLocalRandom.current().nextInt(0, sampleOrganism.getMaxCountPerCell() / 2);

                    for (int i = 0; i < randomCount; i++) {
                        try {
                            locations[row][column].addOrganism((Organism) sampleOrganism.clone());
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException("Error cloning organism: " + sampleOrganism.getName(), e);
                        }
                    }
                }
            }
        }
    }
    public void debugIslandContent() {
        for (int row = 0; row < locations.length; row++) {
            System.out.println("Row " + row + " has " + locations[row].length + " columns.");
            for (int column = 0; column < locations[row].length; column++) {
                Location location = locations[row][column];
                System.out.println("Cell (" + row + ", " + column + ") contains: " +
                        location.getAnimals().size() + " animals, " +
                        location.getPlants().size() + " plants.");
            }
        }
    }
}
