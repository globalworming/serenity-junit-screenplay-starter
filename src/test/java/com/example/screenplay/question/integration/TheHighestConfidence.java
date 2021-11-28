package com.example.screenplay.question.integration;

import com.example.neuralnet.component.HslColor;
import com.example.neuralnet.component.NeuralNetwork;
import com.example.screenplay.ability.AskAndTrainNeuralNetwork;
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
    NeuralNetwork network = AskAndTrainNeuralNetwork.as(actor);
    return network.infer(hslColor).get(0).getConfidence();
  }
}
