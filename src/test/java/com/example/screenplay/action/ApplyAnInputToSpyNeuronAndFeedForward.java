package com.example.screenplay.action;

import com.example.neuralnet.domain.Signal;
import com.example.screenplay.ability.SpyOnNeuron;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class ApplyAnInputToSpyNeuronAndFeedForward implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    val spy = SpyOnNeuron.as(actor);
    spy.accept(Signal.builder().strength(1.).build());
    spy.forward();
  }
}
