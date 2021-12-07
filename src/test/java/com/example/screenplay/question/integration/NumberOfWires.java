package com.example.screenplay.question.integration;

import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

public class NumberOfWires extends QuestionWithDefaultSubject<Integer> {
  @Override
  public Integer answeredBy(Actor actor) {
    return InteractWithNeuralNet.as(actor).getWires().size();
  }
}
