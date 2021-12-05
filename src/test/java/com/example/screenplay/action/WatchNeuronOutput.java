package com.example.screenplay.action;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static com.example.screenplay.ability.InteractWithNeuron.as;

public class WatchNeuronOutput implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    as(actor).connect((d) -> actor.remember(Memory.LATEST_NEURON_OUTPUT, d));
  }
}
