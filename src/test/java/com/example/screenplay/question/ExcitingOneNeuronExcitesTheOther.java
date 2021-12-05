package com.example.screenplay.question;

import com.example.neuralnet.domain.Neuron;
import com.example.neuralnet.domain.Signal;
import com.example.screenplay.ability.InteractWithNeurons;
import lombok.val;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class ExcitingOneNeuronExcitesTheOther extends QuestionWithDefaultSubject<Boolean> {
  private Double output = null;

  @Override
  public Boolean answeredBy(Actor actor) {
    val neurons = InteractWithNeurons.as(actor);
    Neuron inputNeuron = neurons.get(1);
    inputNeuron.connect((it) -> output = it);
    assertThat(output, nullValue());
    neurons.get(0).accept(Signal.builder().strength(1.).build());
    actor.should(
        seeThat(
            "feeding something into the first neuron results in output in the second neuron",
            (a) -> output,
            notNullValue()));
    return true;
  }
}
