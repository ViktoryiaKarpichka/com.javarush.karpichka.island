package com.javarush.island.model;

public enum TypeOrganism {
    BEAR("Bear"),
    BOA("Boa"),
    EAGLE("Eagle"),
    FOX("Fox"),
    WOLF("Wolf"),
    BUFFALO("Buffalo"),
    CATERPILLAR("Caterpillar"),
    DEER("Deer"),
    DUCK("Duck"),
    GOAT("Goat"),
    BOAR("Boar"),
    HORSE("Horse"),
    MOUSE("Mouse"),
    RABBIT("Rabbit"),
    SHEEP("Sheep"),
    PLANT("Plant");

    private final String name;

    TypeOrganism(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
