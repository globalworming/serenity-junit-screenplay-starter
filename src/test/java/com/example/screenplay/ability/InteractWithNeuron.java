package com.example.screenplay.ability;

import com.example.neuralnet.domain.Neuron;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;

@RequiredArgsConstructor
@Getter
public class InteractWithNeuron extends Ability {

  private final Neuron neuron;

  public static Neuron as(Actor actor) {
    return actor.abilityTo(InteractWithNeuron.class).neuron;
  }
}
