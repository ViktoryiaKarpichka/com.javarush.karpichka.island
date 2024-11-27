package com.javarush.island.configuration;

import com.javarush.island.model.TypeOrganism;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface YamlEatingProbability {
    String key();

    TypeOrganism value();

}
