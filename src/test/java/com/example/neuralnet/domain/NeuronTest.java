package com.example.neuralnet.domain;

import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleConsumer;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Narrative(text = "neural nets are made of multiple layers of linked neurons. " +
    "neurons have inputs and corresponding weights. neurons need to calculate and add the inputs " +
    "(they may add a constant bias) and through a sigmoid function produce a value between 0 and 1 as output")
@RunWith(SerenityRunner.class)
public class NeuronTest {

  int black = 0x00;


  @Test
  public void neuronsAcceptInput() {
    val neuron = mock(Neuron.class);
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron accepts input", (a) -> {
      neuron.accept(black);
      return true;
    }));
  }

  @Test
  public void neuronsHaveInputWeight() {
    val neuron = new Neuron();
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron has an input weight", (a) -> neuron.getWeight() != null));
  }

  @Test
  public void increasingWeight() {
    val neuron = new Neuron();
    val actor = Actor.named("tester");

    for (int i = 0; i < 1000; i++) {
      neuron.increaseWeight();

    }
    actor.should(seeThat("the weigh gets very heigh", (a) -> {
      assertThat(neuron.getWeight(), Matchers.greaterThan(10.0));
      return true;
    }));

  }

  @Test
  public void neuronsHaveSigmoidFunction() {
    val neuron = new Neuron();
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron has an sigmoid function", (a) -> neuron.getSigmoidFunction() != null));
  }

  @Test
  public void whenAcceptingAnInputItsWeightedAndConvertedByTheSigmoidFunctionAndSentToTheOutput() {
    Neuron neuron = new Neuron();
    // actor can spy on neuron
    SigmoidFunction mockSigmoidFunction = mock(SigmoidFunction.class);
    DoubleConsumer mockOutput = mock(DoubleConsumer.class);
    Neuron spy = spy(neuron);
    spy.setSigmoidFunction(mockSigmoidFunction);
    spy.setOutputConsumer(mockOutput);


    // actor attemptsTo setup mocked implementation
    double mockWeightedValue = 0.5;

    Mockito.when(spy.applyWeight(black)).thenReturn(mockWeightedValue);
    double mockResult = 0.95;
    Mockito.when(mockSigmoidFunction.apply(mockWeightedValue)).thenReturn(mockResult);


    // actor attempts to put something in the neron
    spy.accept(black);

    // actor attempts to ensure that weight is applied
    verify(spy, times(1)).applyWeight(black);
    // actor attempts to ensure that sigmoid function is called
    verify(mockSigmoidFunction, times(1)).apply(mockWeightedValue);
    // actor attempts to ensure that the output takes the value
    verify(mockOutput, times(1)).accept(mockResult);
  }

  @Test
  public void outputIsGenerated() {
    // actor can test neuron
    val neuron = new Neuron();
    AtomicReference<Double> output = new AtomicReference<>();
    // actor attempts to link oberservability tool
    neuron.setOutputConsumer(output::set);
    // actor attempts to put in a color
    neuron.accept(black);

    // then actor should see that output is between zero and one
    assertThat(output, CoreMatchers.notNullValue());
    Matcher<Double> isValueBetweenZeroAndOne = IsCloseTo.closeTo(0.5, 0.4999);
    assertThat(output.get(), isValueBetweenZeroAndOne);
  }
}