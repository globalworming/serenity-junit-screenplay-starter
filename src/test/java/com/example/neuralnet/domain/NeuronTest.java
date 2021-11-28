package com.example.neuralnet.domain;

import com.example.screenplay.ability.InteractWithNeuron;
import com.example.screenplay.ability.SpyOnNeuron;
import com.example.screenplay.action.ExciteSpyNeuron;
import com.example.screenplay.action.SetupMockedNeuronImplementation;
import com.example.screenplay.question.ApplyWeightIsInvoked;
import com.example.screenplay.question.OutputIsPropagated;
import com.example.screenplay.question.SigmoidFunctionIsInvoked;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.number.IsCloseTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.MatcherAssert.assertThat;

@Narrative(
    text =
        "neural nets are made of multiple layers of linked neurons. "
            + "neurons have inputs and corresponding weights. neurons need to calculate and add the inputs "
            + "(they may add a constant bias) and through a sigmoid function produce a value between 0 and 1 as output")
@RunWith(SerenityRunner.class)
public class NeuronTest {

  private Actor actor = Actor.named("tester");

  @Before
  public void setUp() {
    actor.can(new InteractWithNeuron(new Neuron()));
  }

  @Test
  public void neuronsAcceptInput() {
    actor.should(
        seeThat(
            "a neuron accepts input",
            (a) -> {
              InteractWithNeuron.as(a).accept(0.);
              return true;
            }));
  }

  @Test
  public void neuronsHaveInputWeight() {
    actor.should(
        seeThat(
            "a neuron has an input weight", (a) -> InteractWithNeuron.as(a).getWeight() != null));
  }

  @Test
  public void neuronsHaveSigmoidFunction() {
    actor.should(
        seeThat(
            "a neuron has an sigmoid function",
            (a) -> InteractWithNeuron.as(a).getSigmoidFunction() != null));
  }

  @Test
  public void whenAcceptingAnInputItsWeightedAndConvertedByTheSigmoidFunctionAndSentToTheOutput() {
    actor.can(new SpyOnNeuron(new Neuron()));
    actor.attemptsTo(new SetupMockedNeuronImplementation());
    actor.attemptsTo(new ExciteSpyNeuron());
    actor.should(seeThat(new ApplyWeightIsInvoked()));
    actor.should(seeThat(new SigmoidFunctionIsInvoked()));
    actor.should(seeThat(new OutputIsPropagated()));
  }

  @Test
  public void outputIsGenerated() {

    // actor can test neuron
    val neuron = new Neuron();
    AtomicReference<Double> output = new AtomicReference<>();
    // actor attempts to link oberservability tool
    neuron.setOutputConsumer(output::set);
    // actor attempts to put in a color
    neuron.accept(0.);

    // then actor should see that output is between zero and one
    assertThat(output, CoreMatchers.notNullValue());
    Matcher<Double> isValueBetweenZeroAndOne = IsCloseTo.closeTo(0.5, 0.4999);
    assertThat(output.get(), isValueBetweenZeroAndOne);
  }
}
