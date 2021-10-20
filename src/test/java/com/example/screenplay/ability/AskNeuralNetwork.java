package com.example.screenplay.ability;

import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import starter.NeuralNetwork;

public class AskNeuralNetwork implements Ability {
  private final NeuralNetwork neuralNetwork;

  public AskNeuralNetwork(NeuralNetwork neuralNetwork) {

    this.neuralNetwork = neuralNetwork;
  }

  public static AskNeuralNetwork forColor(NeuralNetwork neuralNetwork) {
    return new AskNeuralNetwork(neuralNetwork);
  }

  public static NeuralNetwork as(Actor actor) {
    return actor.abilityTo(AskNeuralNetwork.class).neuralNetwork;
  }
}