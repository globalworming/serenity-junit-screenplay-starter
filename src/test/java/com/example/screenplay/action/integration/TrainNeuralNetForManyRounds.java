package com.example.screenplay.action.integration;

import com.example.neuralnet.domain.NeuralNet;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.integration.CurrentError;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TrainNeuralNetForManyRounds implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    int rounds = actor.recall(Memory.NUMBER_OF_TRAINING_ROUNDS);
    NeuralNet neuralNet = InteractWithNeuralNet.as(actor);
    List<String> errors = new ArrayList<>();
    errors.add(getAndFormatErrorThisRound(actor, 0));
    Serenity.reportThat(
        String.format(
            "%s starts %s rounds of training, inital error is %s",
            actor, rounds, new CurrentError().answeredBy(actor).toString()),
        () -> {
          for (int i = 0; i < rounds; i++) {
            neuralNet.trainOnFacts();
            errors.add(getAndFormatErrorThisRound(actor, i));
          }
        });
    Serenity.recordReportData()
        .withTitle("errors over rounds")
        .andContents(String.join(",", errors));
  }

  private <T extends Actor> String getAndFormatErrorThisRound(T actor, int i) {
    return i + ": " + new CurrentError().answeredBy(actor).toString();
  }
}
