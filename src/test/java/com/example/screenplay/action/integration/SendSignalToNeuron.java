package com.example.screenplay.action.integration;

import com.example.neuralnet.domain.Signal;
import com.example.neuralnet.domain.Wire;
import com.example.screenplay.ability.InteractWithNeuron;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static java.lang.String.format;
import static net.serenitybdd.core.Serenity.reportThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;

@RequiredArgsConstructor
public class SendSignalToNeuron implements Performable {

  private final Wire source;

  public static Performable from(Wire source) {
    return instrumented(SendSignalToNeuron.class, source);
  }

  @Override
  public void performAs(Actor actor) {
    val neuron = InteractWithNeuron.as(actor);
    val signal = Signal.builder().source(source).strength(.5).build();
    val action = format("send %s from %s to %s", signal, source, neuron);
    reportThat(action, () -> neuron.accept(signal));
  }
}
