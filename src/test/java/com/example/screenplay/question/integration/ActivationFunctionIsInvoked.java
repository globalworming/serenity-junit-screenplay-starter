package com.example.screenplay.question.integration;

import com.example.screenplay.ability.SpyOnNeuron;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ActivationFunctionIsInvoked extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    verify(SpyOnNeuron.as(actor).getActivationFunction(), times(1)).apply(any());
    return true;
  }
}
