package com.example.screenplay.question.integration;

import com.example.neuralnet.component.NeuralNetwork;
import com.example.screenplay.ability.AskNeuralNetwork;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

@RequiredArgsConstructor
public class TheMostLikelyLabel implements Question<String> {

  private final int color;

  public static TheMostLikelyLabel of(int i) {
    return new TheMostLikelyLabel(i);
  }

  @Override
  public String answeredBy(Actor actor) {
    NeuralNetwork network = AskNeuralNetwork.as(actor);
    return network.infer(color).getLabel();
  }
}
