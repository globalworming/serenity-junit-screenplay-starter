package com.example.screenplay.question;

import com.example.screenplay.ability.SpyOnNeuron;
import lombok.val;
import net.serenitybdd.screenplay.Actor;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ApplyWeightIsInvoked extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    val spy = SpyOnNeuron.as(actor);
    verify(spy, times(1)).applyWeight(1.);
    return true;
  }
}
