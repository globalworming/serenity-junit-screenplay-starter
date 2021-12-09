package com.example.screenplay.action.integration;

import com.example.neuralnet.domain.NeuralNet;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.question.integration.CurrentError;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

@RequiredArgsConstructor
public class TrainNeuralNetForManyRounds implements Performable {

  int rounds = 100;

  @Override
  public <T extends Actor> void performAs(T actor) {
    NeuralNet neuralNet = InteractWithNeuralNet.as(actor);
    Serenity.reportThat(
        String.format("%s starts %s rounds of training", actor, rounds),
        () -> {
          for (int i = 0; i < rounds; i++) {
            neuralNet.trainOnFacts();
            Serenity.recordReportData()
                .withTitle("error after round " + i)
                .andContents(new CurrentError().answeredBy(actor).toString());
          }
        });
  }
}
