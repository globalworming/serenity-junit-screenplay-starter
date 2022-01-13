package com.example.screenplay.action.integration;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class TrainNeuralNetwork implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    com.example.screenplay.ability.TrainNeuralNetwork.as(actor)
        .train(actor.recall(Memory.DEFAULT_NUMBER_OF_TRAINING_ROUNDS));
  }
}
