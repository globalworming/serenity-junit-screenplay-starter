package com.example.screenplay.question.integration;

import com.example.screenplay.ability.SpyOnNeuron;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

import java.util.List;
import java.util.function.DoubleConsumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OutputIsPropagated extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    List<DoubleConsumer> outputConsumers = SpyOnNeuron.as(actor).getOutputConsumers();
    outputConsumers.forEach(it -> verify(it, times(1)).accept(1.));
    return true;
  }
}
