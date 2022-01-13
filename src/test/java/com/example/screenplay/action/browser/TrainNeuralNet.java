package com.example.screenplay.action.browser;

import com.example.screenplay.domain.LabeledColor;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;

import java.util.List;

@RequiredArgsConstructor
public class TrainNeuralNet implements Performable {

  private final List<LabeledColor> trainingData;

  public static Performable onDataSet(List<LabeledColor> trainingData) {
    return new TrainNeuralNet(trainingData);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    Serenity.reportThat(
        "when establishing dataset as facts",
        () ->
            trainingData.forEach(
                labeledColor -> {
                  actor.attemptsTo(SelectColor.withValue(labeledColor.getColor()));
                  actor.attemptsTo(Click.on(".e2e-do-reward-for-" + labeledColor.getLabel()));
                }));
    actor.attemptsTo(new StartTraining());
  }
}
