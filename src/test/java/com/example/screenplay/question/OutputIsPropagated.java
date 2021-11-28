package com.example.screenplay.question;

import com.example.screenplay.ability.SpyOnNeuron;
import net.serenitybdd.screenplay.Actor;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OutputIsPropagated extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    verify(SpyOnNeuron.as(actor).getOutputConsumer(), times(1)).accept(1.);
    return true;
  }
}
