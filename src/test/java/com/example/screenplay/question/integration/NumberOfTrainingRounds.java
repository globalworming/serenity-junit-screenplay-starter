package com.example.screenplay.question.integration;

import com.example.screenplay.ability.TrainNeuralNetwork;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

public class NumberOfTrainingRounds extends QuestionWithDefaultSubject<Integer> {

  @Override
  public Integer answeredBy(Actor actor) {
    return TrainNeuralNetwork.as(actor).getTrainingStatistics().getErrors().size();
  }
}
