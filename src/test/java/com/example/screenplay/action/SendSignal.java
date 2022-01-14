package com.example.screenplay.action;

import com.example.neuralnet.domain.Neuron;
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
    val signal = .5;
    val action = format("send %s to %s", signal, neuron);
    reportThat(action, () -> neuron.accept(signal));
  }
}
