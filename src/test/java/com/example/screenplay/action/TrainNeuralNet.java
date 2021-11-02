package com.example.screenplay.action;

import com.example.screenplay.ColorSet;
import com.example.screenplay.ability.AskNeuralNetwork;
import com.example.screenplay.question.integration.TheConfidence;
import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

@AllArgsConstructor
public class TrainNeuralNet implements Performable {

  private final List<ColorSet> trainingData;

  public static Performable onDataSet(List<ColorSet> trainingData) {
    return instrumented(TrainNeuralNet.class, trainingData);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    trainingData.forEach(it -> {
      double confidence = actor.asksFor(TheConfidence.of(it.getColor()));
      if (confidence < .99) {
        AskNeuralNetwork.as(actor).increaseTheWeight();
      }
    });
  }
}
