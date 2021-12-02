package com.example.screenplay.ability;

import com.example.neuralnet.domain.NeuralNet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;

@RequiredArgsConstructor
@Getter
public class InteractWithNeuralNet extends Ability {

  private final NeuralNet neuralNet;

  public static NeuralNet as(Actor actor) {
    return actor.abilityTo(InteractWithNeuralNet.class).neuralNet;
  }
}
