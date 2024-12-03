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
                    .skip(ThreadLocalRandom.current().nextInt(currentLocation.getAnimals().size()))
                    .findFirst()
                    .ifPresentOrElse(plant -> {
                      //  plant.decreaseWeight();
                        currentLocation.removeOrganism(plant);
                        this.setActualSatiety(increaseSatiety(plant));
                        this.increaseWeight(plant);
                    }, () -> handleFailedEating(currentLocation));
        } else if (this instanceof Predators) {

            currentLocation.getAnimals().stream()
                    .filter(prey -> prey != this && canEat(this, prey))
                  //  .skip(ThreadLocalRandom.current().nextInt(currentLocation.getAnimals().size()))
                    .findAny()
                    .ifPresentOrElse(prey -> {
                        if (ThreadLocalRandom.current().nextInt(100) > getProbability(this, prey)) {
                            this.setActualSatiety(increaseSatiety(prey));
                            this.increaseWeight(prey);
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
        double weightGain = o.getWeight() * 0.35;
        this.setWeight(this.getWeight() + weightGain);
    }

    public void decreaseWeight() {
        double weightLoss = this.getWeight() * 0.2; // Потеря 5% веса за цикл
        this.setWeight(this.getWeight() - weightLoss);
        if (this.getWeight() < 0) {
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
            this.setActualSatiety(increaseSatiety(this));
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

        if (sameSpecies.size() > 1 && actualSatiety > maxSatiety * 0.75) { // satiety must be > 85%
            if (ThreadLocalRandom.current().nextDouble() < 0.8) { // 50% chance for repl
                try {
                    Animal offspring = (Animal) this.clone();
                    offspring.setWeight(offspring.getWeight() * 0.5); // offspring weight and satiety less < 70%
                    offspring.setActualSatiety(offspring.getMaxSatiety() * 0.5);
                    currentLocation.addOrganism(offspring);
                } catch (CloneNotSupportedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
