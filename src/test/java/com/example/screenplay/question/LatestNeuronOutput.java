package com.example.screenplay.question;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;

public class LatestNeuronOutput extends QuestionWithDefaultSubject<Double> {
  @Override
  public Double answeredBy(Actor actor) {
    return actor.recall(Memory.LATEST_NEURON_OUTPUT);
  }
}
