package com.example.screenplay.question.integration;

import com.example.neuralnet.component.NeuralNetwork;
import com.example.screenplay.ability.AskAndTrainNeuralNetwork;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

@RequiredArgsConstructor
public class TheConfidence implements Question<Double> {

  private final double color;

  public static TheConfidence of(double i) {
    return new TheConfidence(i);
  }

  @Override
  public Double answeredBy(Actor actor) {
    NeuralNetwork network = AskAndTrainNeuralNetwork.as(actor);
    return network.infer((int) color).getConfidence();
  }
}
