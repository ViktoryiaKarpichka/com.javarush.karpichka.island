package com.javarush.island.entity.animals;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import com.javarush.island.entity.animals.herbivores.Herbivores;
import com.javarush.island.entity.animals.predators.Predators;
import com.javarush.island.interfaces.Eatable;
import com.javarush.island.interfaces.Moveable;
import com.javarush.island.interfaces.Reproducible;
import com.javarush.island.model.Direction;
import com.javarush.island.util.EatingProbabilityUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.javarush.island.configuration.EatingProbabilityConfig.canEat;
import static com.javarush.island.configuration.EatingProbabilityConfig.getProbability;
import static com.javarush.island.util.AnimalUtil.satietyReductionFactor;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class Animal extends Organism implements Eatable, Moveable, Reproducible {

    private int maxSpeed;
    private final double maxSatiety;
    private double actualSatiety;

    public Animal(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell);
        this.maxSpeed = maxSpeed;
        this.maxSatiety = maxSatiety;
        this.actualSatiety = actualSatiety;
    }

    @Override
    public void eat(Location currentLocation) {
        EatingProbabilityUtil.getEatingProbabilityConfig();

        if (this instanceof Herbivores) {
            currentLocation.getPlants().stream()
                    .filter(plant -> plant.getQuantity() > 0)
                    .findFirst()
                    .ifPresentOrElse(plant -> {
                            System.out.println(this.getName() + " ate up a plant.");
                            this.setActualSatiety(increaseSatiety());
                            this.increaseWeight();
                            plant.decreaseQuantity();
                            currentLocation.removeOrganism(plant);
                    }, () -> handleFailedEating(currentLocation));
        } else if (this instanceof Predators) {

            currentLocation.getAnimals().stream()
                    .filter(prey -> prey != this && canEat(this, prey))
                    .skip(ThreadLocalRandom.current().nextInt(currentLocation.getAnimals().size()))
                    .findAny()
                    .ifPresentOrElse(prey -> {
                        int i = ThreadLocalRandom.current().nextInt(100);
                        System.out.println(i);
                        if (i > getProbability(this, prey)) {
                            System.out.println(this.getName() + " ate up " + prey.getName());
                            this.setActualSatiety(increaseSatiety());
                            this.increaseWeight();
                            currentLocation.removeOrganism(prey);
                        } else {
                            System.out.println(prey);
                            handleFailedEating(currentLocation);
                        }
                    }, () -> handleFailedEating(currentLocation));
        }
    }

    private void handleFailedEating(Location currentLocation) {
        System.out.println(this.getName() + " found nothing to eat.");
        this.setActualSatiety(decreaseSatiety());
        this.decreaseWeight();
        if (this.getActualSatiety() <= 0 || this.getWeight() <= 0) {
            System.out.println(this.getName() + " died of hunger.");
            if (currentLocation != null) {
                currentLocation.removeOrganism(this);
            }
        }
    }

    public void increaseWeight() {
        this.setWeight(this.getWeight() + this.getWeight() / 5);
    }

    public void decreaseWeight() {
        this.setWeight(this.getWeight() - this.getWeight() / 5);
    }


    public Direction chooseDirection() {
        Direction[] directions = Direction.values();
        return directions[ThreadLocalRandom.current().nextInt(directions.length)];
    }

    @Override
    public void move(Island island) {
        Location currentLocation = null;
        for (Location[] row : island.getLocations()) {
            currentLocation = Arrays.stream(row)
                    .filter(location -> location.getCurrentLocationOfOrganism(this) != null)
                    .findFirst()
                    .orElse(null);
            if (currentLocation != null) break;
        }

        if (currentLocation == null) {
            throw new IllegalStateException("Organism not found");
        }

        int currentX = currentLocation.getCoordinateX();
        int currentY = currentLocation.getCoordinateY();
        int stepsRemaining = ThreadLocalRandom.current().nextInt(maxSpeed + 1);

        while (stepsRemaining > 0) {
            int targetX = currentX;
            int targetY = currentY;

            switch (chooseDirection()) {
                case UP -> targetY -= 1;
                case DOWN -> targetY += 1;
                case LEFT -> targetX -= 1;
                case RIGHT -> targetX += 1;
            }

            if (!island.isValidCoordinate(targetX, targetY)) {
                break;
            }

            Location targetLocation = island.getLocation(targetX, targetY);

            if (targetLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                targetLocation.addOrganism(this);

                currentX = targetX;
                currentY = targetY;
                currentLocation = targetLocation;
            } else {
                break;
            }
            stepsRemaining--;
        }
    }

    public double increaseSatiety() {
        return this.actualSatiety + (this.maxSatiety * satietyReductionFactor);
    }

    public double decreaseSatiety() {
        return this.actualSatiety - (this.maxSatiety * satietyReductionFactor);
    }

    @Override
    public void reproduce(Location currentLocation) {
        if (!currentLocation.canAddOrganism(this)) {
            return;
        }

        List<Animal> sameSpecies = currentLocation.getAnimals().stream()
                .filter(animal -> animal.getClass().equals(this.getClass()))
                .toList();

        if (sameSpecies.size() > 1) {
            try {
                Animal offspring = (Animal) this.clone();
                currentLocation.addOrganism(offspring);
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException();
            }
        }
    }
}
