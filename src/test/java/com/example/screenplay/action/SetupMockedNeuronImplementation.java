package com.example.screenplay.action;

import com.example.screenplay.ability.SpyOnNeuron;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

public class SetupMockedNeuronImplementation implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    val spy = SpyOnNeuron.as(actor);
    Mockito.when(spy.applyWeight(anyDouble())).thenReturn(1.);
    val sigmoidFunction = spy.getSigmoidFunction();
    doReturn(1.).when(sigmoidFunction).apply(anyDouble());
  }
}
