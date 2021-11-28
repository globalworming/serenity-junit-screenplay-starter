package com.example.screenplay.ability;

public abstract class Ability implements net.serenitybdd.screenplay.Ability {

  @Override
  public String toString() {
    return getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2")
        + ", using the default neural net";
  }
}
