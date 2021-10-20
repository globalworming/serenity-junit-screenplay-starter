package com.example.screenplay.question;

import com.example.screenplay.ability.AskNeuralNetwork;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import starter.NeuralNetwork;

@RequiredArgsConstructor
public class TheColorLabel implements Question<String> {

  private final int color;

  public static TheColorLabel of(int i) {
    return new TheColorLabel(i);
  }

  @Override
  public String answeredBy(Actor actor) {
    NeuralNetwork network = AskNeuralNetwork.as(actor);
    return network.infer(color).getLabel();
  }
}
