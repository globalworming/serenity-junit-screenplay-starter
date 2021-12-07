package com.example.screenplay.action;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.List;

import static com.example.screenplay.ability.InteractWithNeuralNet.as;
import static java.lang.String.format;

@RequiredArgsConstructor
public class EstablishFact implements Performable {
  private final List<Double> input;
  private final List<Double> expectedOutput;

  @Override
  public <T extends Actor> void performAs(T actor) {
    Serenity.reportThat(
        format("given %s we would expect an output close to %s", input, expectedOutput),
        () -> as(actor).addFact(input, expectedOutput));
  }
}
