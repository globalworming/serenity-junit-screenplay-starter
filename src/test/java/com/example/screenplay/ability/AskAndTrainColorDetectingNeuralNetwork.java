package com.example.screenplay.ability;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import net.serenitybdd.screenplay.Actor;

public class AskAndTrainColorDetectingNeuralNetwork extends InteractWithNeuralNet {

  public AskAndTrainColorDetectingNeuralNetwork(
      ColorDetectingNeuralNetwork colorDetectingNeuralNetwork) {
    super(colorDetectingNeuralNetwork);
  }

  public static ColorDetectingNeuralNetwork as(Actor actor) {
    return (ColorDetectingNeuralNetwork)
        actor.abilityTo(AskAndTrainColorDetectingNeuralNetwork.class).getNeuralNet();
  }
}
