package com.example.screenplay.ability;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import net.serenitybdd.screenplay.Actor;

public class AskAndTrainColorDetectingNeuralNetwork extends Ability {
  private final ColorDetectingNeuralNetwork colorDetectingNeuralNetwork;

  public AskAndTrainColorDetectingNeuralNetwork(
      ColorDetectingNeuralNetwork colorDetectingNeuralNetwork) {

    this.colorDetectingNeuralNetwork = colorDetectingNeuralNetwork;
  }

  public static AskAndTrainColorDetectingNeuralNetwork forColor(
      ColorDetectingNeuralNetwork colorDetectingNeuralNetwork) {
    return new AskAndTrainColorDetectingNeuralNetwork(colorDetectingNeuralNetwork);
  }

  public static ColorDetectingNeuralNetwork as(Actor actor) {
    return actor.abilityTo(AskAndTrainColorDetectingNeuralNetwork.class)
        .colorDetectingNeuralNetwork;
  }
}
