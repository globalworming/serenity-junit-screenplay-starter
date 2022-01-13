package com.example.screenplay.action;

import com.example.screenplay.ability.SpyOnNeuron;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

public class SetupMockedNeuronImplementation implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    val spy = SpyOnNeuron.as(actor);
    val sigmoidFunction = spy.getActivationFunction();
    doReturn(.5).when(sigmoidFunction).apply(anyDouble());
  }
}
