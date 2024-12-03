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
                          //  System.out.println(this.getName() + " ate up a plant.");
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
                        if (ThreadLocalRandom.current().nextInt(100) > getProbability(this, prey)) {
                           // System.out.println(this.getName() + " ate up " + prey.getName());
                            this.setActualSatiety(increaseSatiety());
                            this.increaseWeight();
                            currentLocation.removeOrganism(prey);
                        } else {
                         //   System.out.println(prey);
                            handleFailedEating(currentLocation);
                        }
                    }, () -> handleFailedEating(currentLocation));
        }
    }

    private void handleFailedEating(Location currentLocation) {
       // System.out.println(this.getName() + " found nothing to eat.");
        this.setActualSatiety(decreaseSatiety());
        this.decreaseWeight();
        if (this.getActualSatiety() <= 0 || this.getWeight() <= 0) {
          //  System.out.println(this.getName() + " died of hunger.");
            if (currentLocation != null) {
                currentLocation.removeOrganism(this);
            }
        }
    }

    public void increaseWeight() {
        this.setWeight(this.getWeight() + this.getWeight() / 2);
    }

    public void decreaseWeight() {
        this.setWeight(this.getWeight() - this.getWeight() / 2);
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
              //  System.out.println(this.getName() + " cannot move outside boundaries.");
                break;
            }

            if (targetLocation.canAddOrganism(this)) {
                currentLocation.removeOrganism(this);
                targetLocation.addOrganism(this);
              //  System.out.println(this.getName() + " moved to a new location.");
                currentLocation = targetLocation;
            } else {
              //  System.out.println(this.getName() + " cannot move to a full location.");
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
         //   System.out.println("there is no space for the animal");
            return;
        }

        List<Animal> sameSpecies = currentLocation.getAnimals().stream()
                .filter(animal -> animal.getClass().equals(this.getClass()))
                .toList();

        if (sameSpecies.size() > 1) {
            try {
                Animal offspring = (Animal) this.clone();
                currentLocation.addOrganism(offspring);
             //   System.out.println(this.getName() + "the animal has successfully reproduced");
            } catch (CloneNotSupportedException e) {
              //  System.out.println(this.getName() + "the animal has not reproduced");
            }

        } else {
          //  System.out.println(this.getName() + " the animal has not couple");
        }
    }
}
