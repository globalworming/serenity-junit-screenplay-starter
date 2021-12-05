package com.example.screenplay.action;

import com.example.neuralnet.domain.Wire;
import com.example.screenplay.ability.InteractWithNeurons;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

@RequiredArgsConstructor
public class LinksTwoNeurons implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    val neurons = InteractWithNeurons.as(actor);
    val neuron = neurons.get(0);
    val neuronToLink = neurons.get(1);
    Serenity.reportThat(
        String.format("link %s --> %s", neuron, neuronToLink),
        () -> neuron.connect(Wire.builder().source(neuron).target(neuronToLink).build()));
  }
}
