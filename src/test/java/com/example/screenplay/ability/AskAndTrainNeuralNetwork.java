package com.example.screenplay.ability;

import com.example.neuralnet.component.NeuralNetwork;
import net.serenitybdd.screenplay.Actor;

public class AskAndTrainNeuralNetwork extends Ability {
  private final NeuralNetwork neuralNetwork;

  public AskAndTrainNeuralNetwork(NeuralNetwork neuralNetwork) {

    this.neuralNetwork = neuralNetwork;
  }

  public static AskAndTrainNeuralNetwork forColor(NeuralNetwork neuralNetwork) {
    return new AskAndTrainNeuralNetwork(neuralNetwork);
  }

  public static NeuralNetwork as(Actor actor) {
    return actor.abilityTo(AskAndTrainNeuralNetwork.class).neuralNetwork;
  }
}
