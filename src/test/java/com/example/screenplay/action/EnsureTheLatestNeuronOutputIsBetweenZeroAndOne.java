package com.example.screenplay.action;

import com.example.screenplay.question.integration.LatestNeuronOutput;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.hamcrest.Matcher;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class EnsureTheLatestNeuronOutputIsBetweenZeroAndOne implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    Matcher<Double> isValueBetweenZeroAndOne = closeTo(0.5, 0.5);
    actor.should(seeThat(new LatestNeuronOutput(), isValueBetweenZeroAndOne));
  }
}
