package com.example.screenplay.ability;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;

@RequiredArgsConstructor
@Getter
public class InteractWithColorDetectingNeuralNet extends Ability {

  private final ColorDetectingNeuralNetwork neuralNet;

  public static ColorDetectingNeuralNetwork as(Actor actor) {
    return actor.abilityTo(InteractWithColorDetectingNeuralNet.class).neuralNet;
  }
}
