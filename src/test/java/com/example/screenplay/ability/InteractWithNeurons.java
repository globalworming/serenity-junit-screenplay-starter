package com.example.screenplay.ability;

import com.example.neuralnet.domain.Neuron;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class InteractWithNeurons extends Ability {

  private final List<Neuron> neurons;

  public InteractWithNeurons(Neuron... neurons) {
    this.neurons = Arrays.asList(neurons);
  }

  public static List<Neuron> as(Actor actor) {
    return actor.abilityTo(InteractWithNeurons.class).neurons;
  }
}
