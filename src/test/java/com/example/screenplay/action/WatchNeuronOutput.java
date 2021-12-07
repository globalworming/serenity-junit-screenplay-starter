package com.example.screenplay.action;

import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import static com.example.screenplay.ability.InteractWithNeuron.as;

public class WatchNeuronOutput implements Performable {
  public static Performable of(Neuron neuron) {
    return Task.where(
        "{0} connects to " + neuron + " and remembers signals",
        (actor) -> neuron.connect((d) -> actor.remember(Memory.LATEST_NEURON_OUTPUT, d)));
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    as(actor).connect((d) -> actor.remember(Memory.LATEST_NEURON_OUTPUT, d));
  }
}
