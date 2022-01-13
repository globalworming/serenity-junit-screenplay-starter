package com.example.screenplay.action;

import com.example.screenplay.ability.InteractWithNeuralNet;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class WireAllTheNeurons implements Task {
  @Override
  public <T extends Actor> void performAs(T actor) {
    InteractWithNeuralNet.as(actor).wire();
  }
}
