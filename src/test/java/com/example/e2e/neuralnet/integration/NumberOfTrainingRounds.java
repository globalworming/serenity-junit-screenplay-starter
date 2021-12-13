package com.example.e2e.neuralnet.integration;

import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

public class NumberOfTrainingRounds extends QuestionWithDefaultSubject<Integer> {

  @Override
  public Integer answeredBy(Actor actor) {
    return InteractWithNeuralNet.as(actor).getTrainingStatistics().getErrors().size();
  }
}
