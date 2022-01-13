package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.domain.Neuron;
import com.example.neuralnet.domain.Signal;
import com.example.screenplay.ability.InteractWithNeuron;
import com.example.screenplay.ability.SpyOnNeuron;
import com.example.screenplay.action.ApplyAnInputToSpyNeuronAndFeedForward;
import com.example.screenplay.action.SetupMockedNeuronImplementation;
import com.example.screenplay.question.integration.ActivationFunctionIsInvoked;
import com.example.screenplay.question.integration.OutputIsPropagated;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.screenplay.ability.InteractWithNeuron.as;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.notNullValue;

@Narrative(
    text =
        "neural nets are made of multiple layers of linked neurons. neurons have inputs. neurons sum up the inputs (they may add a constant bias) and through a sigmoid function produce a value between 0 and 1 as output and send it forward to other neurons.")
@RunWith(SerenityRunner.class)
public class NeuronIT {

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
  public void neuronsHaveBias() {
    actor.should(seeThat("a neuron has a bias", (a) -> as(a).getBias(), notNullValue()));
  }

  @Test
  public void neuronsHaveActivationFunction() {
    actor.should(
        seeThat(
            "a neuron has an sigmoid function",
            (a) -> as(a).getActivationFunction(),
            notNullValue()));
  }

  @Test
  public void whenAcceptingInputAndFeedingItForward() {
    actor.can(new SpyOnNeuron(new Neuron()));
    actor.attemptsTo(new SetupMockedNeuronImplementation());
    actor.attemptsTo(new ApplyAnInputToSpyNeuronAndFeedForward());
    actor.should(seeThat(new ActivationFunctionIsInvoked()));
    actor.should(seeThat(new OutputIsPropagated()));
  }
}
