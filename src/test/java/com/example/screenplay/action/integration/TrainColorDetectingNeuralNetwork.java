package com.example.screenplay.action.integration;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import com.example.neuralnet.domain.LabeledHslColor;
import com.example.neuralnet.domain.NeuralNetTrainer;
import com.example.screenplay.actor.Memory;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class TrainColorDetectingNeuralNetwork implements Performable {
  public static Performable onDataSet(List<LabeledHslColor> trainingData) {
    return instrumented(TrainColorDetectingNeuralNetOnDataSet.class, trainingData);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    com.example.screenplay.ability.TrainColorDetectingNeuralNetwork.as(actor)
        .train(actor.recall(Memory.DEFAULT_NUMBER_OF_TRAINING_ROUNDS));
  }

  @RequiredArgsConstructor
  public static class TrainColorDetectingNeuralNetOnDataSet implements Performable {
    private final List<LabeledHslColor> trainingData;

    @Override
    public <T extends Actor> void performAs(T actor) {
      NeuralNetTrainer neuralNetTrainer =
          com.example.screenplay.ability.TrainColorDetectingNeuralNetwork.as(actor);
      Serenity.recordReportData()
          .withTitle("data")
          .andContents(
              trainingData.stream()
                  .map(LabeledHslColor::toString)
                  .collect(Collectors.joining(", ")));
      neuralNetTrainer.getFacts().clear();
      Serenity.reportThat(
          format("on dataset of size %d", trainingData.size()),
          () ->
              trainingData.forEach(
                  (fact) -> {
                    List<Double> input = ColorDetectingNeuralNetwork.toInputs(fact.getHslColor());
                    List<Double> output =
                        neuralNetTrainer.getNeuralNet().getOutputNeurons().stream()
                            .map(it -> it.getLabel().equals(fact.getLabel()) ? 1. : 0.)
                            .collect(Collectors.toList());
                    neuralNetTrainer.addFact(input, output);
                  }));
      neuralNetTrainer.train(actor.recall(Memory.DEFAULT_NUMBER_OF_TRAINING_ROUNDS));
    }
  }
}
