package com.javarush.island.entity;

import com.javarush.island.entity.animals.Animal;
import com.javarush.island.entity.plants.Plant;
import com.javarush.island.model.Direction;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Setter
@Getter
public class Location implements Runnable{
    private int coordinateX;
    private int coordinateY;
    private final Lock lock = new ReentrantLock(true);
    private final List<Organism> organisms = new CopyOnWriteArrayList<>();
    private final Map<Direction, Location> neighbors = new EnumMap<>(Direction.class);

    public Location(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public synchronized void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public synchronized void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }

    public void setNeighbor(Direction direction, Location neighbor) {
        neighbors.put(direction, neighbor);
    }

    public Location getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }

    public List<Animal> getAnimals() {
        return organisms.stream()
                .filter(organism -> organism instanceof Animal)
                .map(organism -> (Animal) organism).toList();
    }

    public List<Plant> getPlants() {
        return organisms.stream()
                .filter(organism -> organism instanceof Plant)
                .map(organism -> (Plant) organism).toList();
    }

    public boolean canAddOrganism(Organism organism) {
        long sameTypeCount = organisms.stream()
                .filter(o -> o.getClass().equals(organism.getClass()))
                .count();
        return sameTypeCount < organism.getMaxCountPerCell();
    }

    @Override
    public void run() {
        lock.lock();
        try {
            getAnimals().forEach(animal -> {
                try {
                    animal.eat(this);
                    animal.reproduce(this);
                    animal.move(this);
                } catch (Exception e) {
                    System.err.println("Error processing animal: " + animal.getName() + " at (" + coordinateX + ", " + coordinateY + ")");
                    System.out.println(e.getMessage());
                }
            });
        } finally {
            lock.unlock();
        }
    }
}
