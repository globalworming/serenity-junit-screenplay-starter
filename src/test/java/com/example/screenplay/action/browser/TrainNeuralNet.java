package com.example.screenplay.action.browser;

import com.example.e2e.browser.NeuralNetIT.ColorSet;
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
    trainingData.forEach(
        it -> {
          actor.attemptsTo(SelectColor.withValue(it.getColor()));
          actor.attemptsTo(new RewardFor(it.getLabel()));
        });
  }
}
