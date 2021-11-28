package com.example.screenplay.question;

import com.example.screenplay.ability.SpyOnNeuron;
import net.serenitybdd.screenplay.Actor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SigmoidFunctionIsInvoked extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    verify(SpyOnNeuron.as(actor).getSigmoidFunction(), times(1)).apply(any());
    return true;
  }
}
