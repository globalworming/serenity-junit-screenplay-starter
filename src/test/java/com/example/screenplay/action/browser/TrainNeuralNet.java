package com.example.screenplay.action.browser;

import com.example.screenplay.domain.LabeledColor;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

import java.util.List;

@RequiredArgsConstructor
public class TrainNeuralNet implements Performable {

  private final List<LabeledColor> trainingData;

  public static Performable onDataSet(List<LabeledColor> trainingData) {
    return new TrainNeuralNet(trainingData);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    trainingData.forEach(
        labeledColor -> {
          actor.attemptsTo(SelectColor.withValue(labeledColor.getColor()));
          actor.attemptsTo(Click.on(".e2e-do-reward-for-" + labeledColor.getLabel()));
        });
    actor.attemptsTo(Click.on(Target.the("train a few rounds").locatedBy(".e2e-do-train")));
  }
}
