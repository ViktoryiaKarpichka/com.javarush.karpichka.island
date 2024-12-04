package com.javarush.island.entity.animals;

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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.javarush.island.configuration.EatingProbabilityConfig.canEat;
import static com.javarush.island.configuration.EatingProbabilityConfig.getProbability;
import static com.javarush.island.util.AnimalUtil.SATIETY_REDUCTION;

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
                        .filter(plant -> plant.getWeight() > 0)
                        .findFirst()
                        .ifPresentOrElse(plant -> {
                            if (ThreadLocalRandom.current().nextDouble() < 0.3) {
                                currentLocation.removeOrganism(plant);
                           }
                            this.setActualSatiety(increaseSatiety(plant));
                        }, () -> handleFailedEating(currentLocation));
        } else if (this instanceof Predators) {
            currentLocation.getAnimals().stream()
                    .filter(prey -> prey != this && canEat(this, prey))
                    .findFirst()
                    .ifPresentOrElse(prey -> {
                        if (ThreadLocalRandom.current().nextInt(100) < getProbability(this, prey)) {
                            this.setActualSatiety(increaseSatiety(prey));
                            increaseWeight(prey);
                            currentLocation.removeOrganism(prey);
                        } else {
                            handleFailedEating(currentLocation);
                        }
                    }, () -> handleFailedEating(currentLocation));
        }
    }

    private void handleFailedEating(Location currentLocation) {
        this.setActualSatiety(decreaseSatiety(this));
        this.decreaseWeight();
        if (this.getActualSatiety() <= 0 || this.getWeight() <= 0) {
            if (currentLocation != null) {
                currentLocation.removeOrganism(this);
            }
        }
    }

    public void increaseWeight(Organism o) {
        double weightGain = o.getWeight() * 0.2;
        this.setWeight(this.getWeight() + weightGain);
    }

    public void decreaseWeight() {
        double weightLoss = this.getWeight() * 0.5; //
        this.setWeight(this.getWeight() - weightLoss);
        if (this.getWeight() <= 0) {
            this.setWeight(0);
        }
    }


    public Direction chooseDirection() {
        Direction[] directions = Direction.values();
        return directions[ThreadLocalRandom.current().nextInt(directions.length)];
    }

    @Override
    public void move(Location currentLocation) {

        int stepsRemaining = ThreadLocalRandom.current().nextInt(0, maxSpeed + 1);

        while (stepsRemaining > 0) {
            Direction direction = chooseDirection();
            Location targetLocation = currentLocation.getNeighbor(direction);

            if (targetLocation == null) {
                break;
            }

            if (targetLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                targetLocation.addOrganism(this);
                currentLocation = targetLocation;
            } else {
                break;
            }
            this.setActualSatiety(decreaseSatiety(this));
            decreaseWeight();
            stepsRemaining--;

        }
    }

    public double increaseSatiety(Organism o) {
        return this.actualSatiety + (o.getWeight()/maxSatiety);
    }

    public double decreaseSatiety(Organism o) {
        return this.actualSatiety - (o.getWeight()/maxSatiety);
    }

    @Override
    public void reproduce(Location currentLocation) {
        if (!currentLocation.canAddOrganism(this)) {
            return;
        }

        List<Animal> sameSpecies = currentLocation.getAnimals().stream()
                .filter(animal -> animal.getClass().equals(this.getClass()))
                .toList();

        if (sameSpecies.size() > 1 && actualSatiety == maxSatiety ) { //only with maxSatiety
            if (ThreadLocalRandom.current().nextDouble() < 0.5) { // 50% chance for repl
                try {
                    Animal offspring = (Animal) this.clone();
                    offspring.setWeight(offspring.getWeight() / 2); // offspring weight and satiety less then offspring
                    offspring.setActualSatiety(offspring.getMaxSatiety() * SATIETY_REDUCTION);
                    currentLocation.addOrganism(offspring);
                } catch (CloneNotSupportedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
