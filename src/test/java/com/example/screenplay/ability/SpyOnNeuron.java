package com.example.screenplay.ability;

import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.Neuron;
import lombok.Getter;
import net.serenitybdd.screenplay.Actor;

import java.util.function.DoubleConsumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Getter
public class SpyOnNeuron extends Ability {

  private final Neuron neuron;

  public SpyOnNeuron(Neuron neuron) {
    ActivationFunction mockActivationFunction = mock(ActivationFunction.class);
    DoubleConsumer mockOutput = mock(DoubleConsumer.class);
    neuron.setActivationFunction(mockActivationFunction);
    neuron.connect(mockOutput);
    this.neuron = spy(neuron);
  }

  public static Neuron as(Actor actor) {
    return actor.abilityTo(SpyOnNeuron.class).neuron;
  }
}
