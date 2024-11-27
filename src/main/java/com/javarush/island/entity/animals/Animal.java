package com.javarush.island.entity.animals;

import com.javarush.island.configuration.EatingProbabilityConfig;
import com.javarush.island.entity.Location;
import com.javarush.island.entity.Organism;
import com.javarush.island.interfaces.Eatable;
import com.javarush.island.interfaces.Moveable;
import com.javarush.island.interfaces.Reproducible;
import com.javarush.island.model.Direction;
import com.javarush.island.util.AnimalUtil;
import com.javarush.island.util.EatingProbabilityUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
        if (EatingProbabilityUtil.eatingProbabilityConfig.canEat(this, prey) &&
                this.getActualSatiety() <= this.getMaxSatiety()) {
            if (Math.random() * 100 > EatingProbabilityConfig.getProbability(this, prey)) {
                System.out.println(this.getName() + " съел " + prey.getName());
                this.setActualSatiety(increaseSatiety());
            } else {
                System.out.println(this.getName() + " не смог съесть " + prey.getName());
                this.setActualSatiety(decreaseSatiety());
            }
        }
    }

    // передвигаться (в соседние локации){
    // а есть ли место для этого вида в новой локации?
    @Override
    public void move(Direction direction) {

    }

    // размножаться (при наличии пары в их локации),
    @Override
    public void reproduce() {

    }

    public double increaseSatiety() {
        return this.actualSatiety - this.getMaxSatiety() * AnimalUtil.satietyReductionFactor;
    }

    public double decreaseSatiety() {
        return this.actualSatiety - this.getMaxSatiety() * AnimalUtil.satietyReductionFactor;
    }

    //должен определить в какую локацию МОЖНО идти
    public void chooseDirection(Location location) {

    }
}
