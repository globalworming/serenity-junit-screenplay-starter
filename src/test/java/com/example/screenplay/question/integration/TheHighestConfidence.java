package com.example.screenplay.question.integration;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import com.example.neuralnet.component.HslColor;
import com.example.screenplay.ability.AskAndTrainColorDetectingNeuralNetwork;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

@RequiredArgsConstructor
public class TheHighestConfidence implements Question<Double> {

  private final HslColor hslColor;

  public static TheHighestConfidence of(HslColor hslColor) {
    return new TheHighestConfidence(hslColor);
  }

  @Override
  public Double answeredBy(Actor actor) {
    ColorDetectingNeuralNetwork network = AskAndTrainColorDetectingNeuralNetwork.as(actor);
    return network.infer(hslColor).get(0).getConfidence();
  }
}
