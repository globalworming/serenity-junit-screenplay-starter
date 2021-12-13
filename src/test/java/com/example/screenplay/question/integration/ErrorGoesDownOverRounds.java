package com.example.screenplay.question.integration;

import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

import static java.lang.String.format;

public class ErrorGoesDownOverRounds extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    List<Double> errors = InteractWithNeuralNet.as(actor).getTrainingStatistics().getErrors();
    Serenity.reportThat(
        format("error list %s should be sorted and decreasing", errors),
        () -> {
          for (int i = 0; i < errors.size() - 1; i++) {
            if (errors.get(i) < errors.get(i + 1)) {
              throw new AssertionError();
            }
          }
          if (errors.get(0) < errors.get(errors.size() - 1)) {
            throw new AssertionError();
          }
        });
    return true;
  }
}
