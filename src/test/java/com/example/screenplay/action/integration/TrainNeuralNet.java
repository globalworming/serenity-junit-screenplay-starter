package com.example.screenplay.action.integration;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

@RequiredArgsConstructor
public class TrainNeuralNet implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    throw new RuntimeException("TODO");
  }
}
