package com.javarush.island;

import com.javarush.island.entity.Island;
import com.javarush.island.model.Statistic;
import com.javarush.island.services.Game;

import static com.javarush.island.util.IslandConfigUtil.LIFE_CYCLE;

public class IslandApp {
    public static void main(String[] args) {
        Island island = new Island();
        island.fillingLocations();
        Statistic statistic = new Statistic(island);
        Game game = new Game(island, statistic);

        System.out.println("Simulation started...");
        game.start();
        try {
            Thread.sleep(LIFE_CYCLE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            game.shutdown();
        }

        System.out.println("Simulation finished.");
      //  island.debugIslandContent();
    }
}
