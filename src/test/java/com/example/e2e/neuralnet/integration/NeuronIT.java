package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.domain.Neuron;
import com.example.neuralnet.domain.Signal;
import com.example.neuralnet.domain.Wire;
import com.example.screenplay.ability.InteractWithNeuron;
import com.example.screenplay.ability.SpyOnNeuron;
import com.example.screenplay.action.ApplyAnInputToSpyNeuron;
import com.example.screenplay.action.SendSignalToNeuron;
import com.example.screenplay.action.SetupMockedNeuronImplementation;
import com.example.screenplay.action.WatchNeuronOutput;
import com.example.screenplay.question.integration.LatestNeuronOutput;
import com.example.screenplay.question.integration.OutputIsPropagated;
import com.example.screenplay.question.integration.SigmoidFunctionIsInvoked;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.screenplay.ability.InteractWithNeuron.as;
import static net.serenitybdd.core.Serenity.reportThat;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.number.IsCloseTo.closeTo;

@Narrative(
    text =
        "neural nets are made of multiple layers of linked neurons. neurons have inputs. neurons sum up the inputs (they may add a constant bias) and through a sigmoid function produce a value between 0 and 1 as output and send it forward to other neurons.")
@RunWith(SerenityRunner.class)
public class NeuronIT {

  private Wire someSource = Wire.builder().build();
  private Wire someOtherSource = Wire.builder().build();
  private Actor actor = Actor.named("tester");

  @Before
  public void setUp() {
    actor.can(new InteractWithNeuron(new Neuron()));
  }

  @Test
  public void neuronsAcceptInput() {
    actor.should(
        seeThat(
            "a neuron accepts signals",
            (a) -> {
              as(a).accept(Signal.builder().build());
              return true;
            }));
  }

  @Test
  public void neuronsKeepTrackOfMultipleInputs() {
    actor.attemptsTo(SendSignalToNeuron.from(someSource));
    actor.attemptsTo(SendSignalToNeuron.from(someOtherSource));
    actor.should(
        seeThat(
            "number of inputs the neuron is aware of",
            (a) -> as(a).getInputToStrength().keySet().size(),
            is(2)));
  }

  @Test
  public void neuronsHaveBias() {
    actor.should(seeThat("a neuron has a bias", (a) -> as(a).getBias(), notNullValue()));
  }

  @Test
  public void neuronsHaveSigmoidFunction() {
    actor.should(
        seeThat(
            "a neuron has an sigmoid function",
            (a) -> as(a).getActivationFunction(),
            notNullValue()));
  }

  @Test
  public void whenAcceptingAnInputTheSigmoidFunctionIsAppliedAndSentToTheOutput() {
    actor.can(new SpyOnNeuron(new Neuron()));
    actor.attemptsTo(new SetupMockedNeuronImplementation());
    actor.attemptsTo(new ApplyAnInputToSpyNeuron());
    actor.should(seeThat(new SigmoidFunctionIsInvoked()));
    actor.should(seeThat(new OutputIsPropagated()));
  }

  @Test
  public void neuronsSendOutValueBetweenZeroAndOne() {
    actor.attemptsTo(new WatchNeuronOutput());
    actor.attemptsTo(SendSignalToNeuron.from(Wire.builder().build()));
    Matcher<Double> isValueBetweenZeroAndOne = closeTo(0.5, 0.5);
    actor.should(seeThat(new LatestNeuronOutput(), isValueBetweenZeroAndOne));
  }

  @Test
  public void outputFromMultipleInputsIsTakenIntoAccount() {

    actor.attemptsTo(new WatchNeuronOutput());
    actor.attemptsTo(SendSignalToNeuron.from(someSource));
    double firstOutput = actor.asksFor(new LatestNeuronOutput());
    actor.attemptsTo(SendSignalToNeuron.from(someOtherSource));
    double secondOutput = actor.asksFor(new LatestNeuronOutput());
    reportThat(
        "more input from different sources results in higher output",
        () -> assertThat(secondOutput, greaterThan(firstOutput)));
  }
}
