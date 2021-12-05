package com.example.screenplay.action;

import com.example.screenplay.LabeledColor;
import com.example.screenplay.ability.AskAndTrainNeuralNetwork;
import lombok.AllArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.Tasks.instrumented;

@AllArgsConstructor
public class TrainNeuralNet implements Performable {

  private final List<LabeledColor> trainingData;

  public static Performable onDataSet(List<LabeledColor> trainingData) {
    return instrumented(TrainNeuralNet.class, trainingData);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    Serenity.recordReportData()
        .withTitle("data")
        .andContents(
            trainingData.stream().map(LabeledColor::toString).collect(Collectors.joining(", ")));
    trainingData.forEach(it -> AskAndTrainNeuralNetwork.as(actor).reward(it.getLabel()));
  }
}
