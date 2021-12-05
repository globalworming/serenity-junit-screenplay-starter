package com.example.screenplay.question;

import com.example.screenplay.ability.InteractWithNeuralNet;
import net.serenitybdd.screenplay.Actor;

public class NumberOfWires extends QuestionWithDefaultSubject<Integer> {
  @Override
  public Integer answeredBy(Actor actor) {
    return InteractWithNeuralNet.as(actor).getWires().size();
  }
}
