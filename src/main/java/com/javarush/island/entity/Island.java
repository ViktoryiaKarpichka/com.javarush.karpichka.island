package com.javarush.island.entity;

import com.javarush.island.configuration.IslandConfig;
import com.javarush.island.util.IslandUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    public Location getLocation(int x, int y) {
        if (isValidCoordinate(x, y)) {
            return locations[y][x];
        }
        throw new IllegalArgumentException("Invalid coordinates: x=" + x + ", y=" + y);
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < widthIsland && y >= 0 && y < lengthIsland;
    }

    public List<Location> getNeighborLocations(int x, int y) {
        List<Location> neighbors = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx;
                int ny = y + dy;
                if (isValidCoordinate(nx, ny)) {
                    neighbors.add(locations[ny][nx]);
                }
            }
        }
        return neighbors;
    }

    public void reset() {
        for (int row = 0; row < lengthIsland; row++) {
            for (int column = 0; column < widthIsland; column++) {
                locations[row][column].getOrganisms().clear();
            }
        }
    }

//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for (int row = 0; row < height; row++) {
//            for (int column = 0; column < width; column++) {
//                sb.append("[").append(locations[row][column].getOrganisms().size()).append("] ");
//            }
//            sb.append("\n");
//        }
//        return sb.toString();
//    }
}
