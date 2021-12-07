package com.example.screenplay.action;

import com.example.screenplay.ability.AskAndTrainColorDetectingNeuralNetwork;
import com.example.screenplay.domain.LabeledHslColor;
import lombok.AllArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.Tasks.instrumented;

@AllArgsConstructor
public class TrainColorDetectingNeuralNet implements Performable {

  private final List<LabeledHslColor> trainingData;

  public static Performable onDataSet(List<LabeledHslColor> trainingData) {
    return instrumented(TrainColorDetectingNeuralNet.class, trainingData);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    Serenity.recordReportData()
        .withTitle("data")
        .andContents(
            trainingData.stream().map(LabeledHslColor::toString).collect(Collectors.joining(", ")));
    trainingData.forEach(
        it -> AskAndTrainColorDetectingNeuralNetwork.as(actor).reward(it.getLabel()));
  }
}
