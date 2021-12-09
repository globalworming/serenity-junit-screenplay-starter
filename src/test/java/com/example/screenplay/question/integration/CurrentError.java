package com.example.screenplay.question.integration;

import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

public class CurrentError extends QuestionWithDefaultSubject<Double> {
  @Override
  public Double answeredBy(Actor actor) {
    return InteractWithNeuralNet.as(actor).calculateCurrentError();
  }
}
