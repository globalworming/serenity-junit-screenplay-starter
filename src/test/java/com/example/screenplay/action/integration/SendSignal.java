package com.example.screenplay.action.integration;

import com.example.neuralnet.domain.Neuron;
import com.example.neuralnet.domain.Signal;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static java.lang.String.format;
import static net.serenitybdd.core.Serenity.reportThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;

@RequiredArgsConstructor
public class SendSignal implements Performable {
  private final Neuron neuron;

  public static SendSignal toNeuron(Neuron inputNeuron) {
    return instrumented(SendSignal.class, inputNeuron);
  }

  @Override
  public <T extends Actor> void performAs(T t) {
    val signal = Signal.builder().strength(.5).build();
    val action = format("send %s to %s", signal, neuron);
    reportThat(action, () -> neuron.accept(signal));
  }
}
