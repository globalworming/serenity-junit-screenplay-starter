package com.example.screenplay.action.browser;

import com.example.screenplay.domain.ColorSet;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;

import java.util.List;

@RequiredArgsConstructor
public class TrainNeuralNet implements Performable {

  private final List<ColorSet> trainingData;

  public static Performable onDataSet(List<ColorSet> trainingData) {
    return new TrainNeuralNet(trainingData);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    trainingData.forEach(
        colorSet -> {
          actor.attemptsTo(SelectColor.withValue(colorSet.getColor()));
          actor.attemptsTo(Click.on(".e2e-do-reward-for-" + colorSet.getLabel()));
        });
  }
}
