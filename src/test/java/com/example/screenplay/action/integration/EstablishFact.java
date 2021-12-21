package com.example.screenplay.action.integration;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.List;

import static com.example.screenplay.ability.TrainNeuralNetwork.as;
import static java.lang.String.format;

@RequiredArgsConstructor
public class EstablishFact implements Performable {
  private final List<Double> input;
  private final List<Double> expectedOutput;

  @Override
  public <T extends Actor> void performAs(T actor) {
    Serenity.reportThat(
        format("when %s adds fact: the input %s should output %s", actor, input, expectedOutput),
        () -> as(actor).addFact(input, expectedOutput));
  }
}
