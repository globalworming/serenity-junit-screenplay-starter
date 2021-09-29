package com.example.screenplay.question;

import com.example.screenplay.ability.AskNeuralNetwork;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import starter.NeuralNetwork;

public class TheColor implements Question<String> {

  public static TheColor of(int i) {
    return new TheColor();
  }

  @Override
  public String answeredBy(Actor actor) {
    NeuralNetwork network = AskNeuralNetwork.as(actor);
    return "fixme";
  }
}
