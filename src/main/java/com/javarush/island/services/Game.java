package com.javarush.island.services;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.Location;
import com.javarush.island.model.Statistic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    public static final int CORE_PUL_SIZE = 4;
    private final Island island;
    private final Statistic statistic;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(CORE_PUL_SIZE);
    private final ExecutorService cellExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Game(Island island, Statistic statistic) {
        this.island = island;
        this.statistic = statistic;
    }

    public void start() {

        scheduler.scheduleAtFixedRate(this::growPlants, 0, 1, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(this::runAnimalLifeCycle, 0, 1, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(this::printStatistics, 0, 1, TimeUnit.SECONDS);
    }

    private void growPlants() {
        System.out.println("Growing plants...");
        for (Location[] row : island.getLocations()) {
            for (Location location : row) {
                location.getPlants().forEach(plant -> plant.reproduce(location));
            }
        }
    }

    private void runAnimalLifeCycle() {
        System.out.println("Running animal life cycle...");

        for (Location[] row : island.getLocations()) {
            for (Location location : row) {
                cellExecutor.execute(location::run);
            }
        }
    }

    private void printStatistics() {
        statistic.printStats();
        if (statistic.shouldTerminateSimulation()) {
            System.out.println("Simulation over! One or more species have gone extinct.");
            shutdown();
        }
    }

    public void shutdown() {
        try {
            scheduler.shutdown();
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
            cellExecutor.shutdown();
            if (!cellExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                cellExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            scheduler.shutdownNow();
            cellExecutor.shutdownNow();
        }
    }
}
