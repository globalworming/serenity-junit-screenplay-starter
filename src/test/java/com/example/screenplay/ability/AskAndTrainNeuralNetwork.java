package com.example.screenplay.ability;

import com.example.neuralnet.component.NeuralNetwork;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

public class AskAndTrainNeuralNetwork implements Ability {
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

  @Override
  public String toString() {
    return getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2")
        + ", using the default neural net";
  }
}
