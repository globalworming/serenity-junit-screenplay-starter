package com.example.screenplay.action.integration;

import com.example.screenplay.ability.TrainColorDetectingNeuralNetwork;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.List;

public class AddSmallSampleFactsForGrayWhiteBlackLabels implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    val neuralNetTrainer = TrainColorDetectingNeuralNetwork.as(actor);
    Serenity.reportThat(
        "add facts for black clustering at low lightness",
        () -> {
          neuralNetTrainer.addFact(List.of(.0, .0, .000), List.of(1., 0., 0.));
          neuralNetTrainer.addFact(List.of(.0, .0, .001), List.of(1., 0., 0.));
          neuralNetTrainer.addFact(List.of(.0, .0, .01), List.of(1., 0., 0.));
          neuralNetTrainer.addFact(List.of(.0, .0, .05), List.of(1., 0., 0.));
          neuralNetTrainer.addFact(List.of(.0, .0, .1), List.of(1., 0., 0.));
        });
    Serenity.reportThat(
        "add facts for gray clustering at medium lightness",
        () -> {
          neuralNetTrainer.addFact(List.of(.0, .0, .5), List.of(.0, 1., 0.));
          neuralNetTrainer.addFact(List.of(.0, .0, .8), List.of(.0, 1., 0.));
          neuralNetTrainer.addFact(List.of(.0, .0, .2), List.of(.0, 1., 0.));
          neuralNetTrainer.addFact(List.of(.0, .0, .7), List.of(.0, 1., 0.));
          neuralNetTrainer.addFact(List.of(.0, .0, .6), List.of(.0, 1., 0.));
        });
    Serenity.reportThat(
        "add facts for white clustering at high lightness",
        () -> {
          neuralNetTrainer.addFact(List.of(.0, .0, 1.), List.of(0., 0., 1.));
          neuralNetTrainer.addFact(List.of(.0, .0, .97), List.of(0., 0., 1.));
          neuralNetTrainer.addFact(List.of(.0, .0, .95), List.of(0., 0., 1.));
          neuralNetTrainer.addFact(List.of(.0, .0, .9), List.of(0., 0., 1.));
          neuralNetTrainer.addFact(List.of(.0, .0, .87), List.of(0., 0., 1.));
        });
  }
}
