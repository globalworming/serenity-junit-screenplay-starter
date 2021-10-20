package com.example.screenplay.question;

import com.example.screenplay.ability.AskNeuralNetwork;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import starter.NeuralNetwork;

@RequiredArgsConstructor
public class TheConfidence implements Question<Double> {

  private final double color;

  public static TheConfidence of(double i) {
    return new TheConfidence(i);
  }

  @Override
  public Double answeredBy(Actor actor) {
    NeuralNetwork network = AskNeuralNetwork.as(actor);
    return network.infer(color).getConfidence();
  }
}
