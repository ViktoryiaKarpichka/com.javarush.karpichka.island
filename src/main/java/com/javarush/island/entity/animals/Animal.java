package com.javarush.island.entity.animals;

import com.javarush.island.entity.Island;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import com.javarush.island.interfaces.Eatable;
import com.javarush.island.interfaces.Moveable;
import com.javarush.island.interfaces.Reproducible;
import com.javarush.island.model.Direction;
import com.javarush.island.util.EatingProbabilityUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
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
    private double actualSatiety;  // (Фактическая сытость) - в течение ЖЦ животного, значение этого поля должно уменьшаться (или увеличиваться когда поел)

    public Animal(String name, double weight, int maxCountPerCell, int maxSpeed, double maxSatiety, double actualSatiety) {
        super(name, weight, maxCountPerCell);
        this.maxSpeed = maxSpeed;
        this.maxSatiety = maxSatiety;
        this.actualSatiety = actualSatiety;
    }

    @Override
    public void eat(Organism prey) {
        EatingProbabilityUtil.getEatingProbabilityConfig();
        if (canEat(this, prey) &&
                this.getActualSatiety() <= this.getMaxSatiety()) {
            if (Math.random() * 100 > getProbability(this, prey)) {
                System.out.println(this.getName() + " съел " + prey.getName());
                this.setActualSatiety(increaseSatiety());
            } else {
                System.out.println(this.getName() + " не смог съесть " + prey.getName());
                this.setActualSatiety(decreaseSatiety());
            }
        }
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
                break; // Достигли края острова, перемещение остановлено
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

    // размножаться (при наличии пары в их локации),
    @Override
    public void reproduce() {
//        Location currentLocation = IslandUtil.getLocationForAnimal(this);
//        if (currentLocation == null) return;
//
//        List<Animal> sameSpecies = currentLocation.getAnimals().stream()
//                .filter(animal -> animal.getClass().equals(this.getClass()))
//                .collect(Collectors.toList());
//
//        if (sameSpecies.size() >= 2 && sameSpecies.size() < this.getMaxCountPerCell()) {
//            Animal offspring = this.createOffspring();
//            currentLocation.addOrganism(offspring);
//            IslandUtil.updateLocationForAnimal(offspring, currentLocation);
//            System.out.println(this.getName() + " размножился!");
//        }

    }

    public double increaseSatiety() {
        return this.actualSatiety - (this.getMaxSatiety() * satietyReductionFactor);
    }

    public double decreaseSatiety() {
        return this.actualSatiety - (this.getMaxSatiety() * satietyReductionFactor);
    }

}
