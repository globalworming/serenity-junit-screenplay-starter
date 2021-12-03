package com.example.screenplay.ability;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import net.serenitybdd.screenplay.Actor;

public class AskAndTrainNeuralNetwork extends Ability {
  private final ColorDetectingNeuralNetwork colorDetectingNeuralNetwork;

  public AskAndTrainNeuralNetwork(ColorDetectingNeuralNetwork colorDetectingNeuralNetwork) {

    this.colorDetectingNeuralNetwork = colorDetectingNeuralNetwork;
  }

  public static AskAndTrainNeuralNetwork forColor(
      ColorDetectingNeuralNetwork colorDetectingNeuralNetwork) {
    return new AskAndTrainNeuralNetwork(colorDetectingNeuralNetwork);
  }

  public static ColorDetectingNeuralNetwork as(Actor actor) {
    return actor.abilityTo(AskAndTrainNeuralNetwork.class).colorDetectingNeuralNetwork;
  }
}
