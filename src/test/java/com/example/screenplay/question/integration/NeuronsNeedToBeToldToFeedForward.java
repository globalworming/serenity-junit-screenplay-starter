package com.example.screenplay.question.integration;

import com.example.screenplay.ability.InteractWithNeurons;
import com.example.screenplay.action.EnsureTheLatestNeuronOutputIsBetweenZeroAndOne;
import com.example.screenplay.action.SendSignal;
import com.example.screenplay.action.WatchNeuronOutput;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.val;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.core.Serenity.reportThat;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.nullValue;

public class NeuronsNeedToBeToldToFeedForward extends QuestionWithDefaultSubject<Boolean> {

  @Override
  public Boolean answeredBy(Actor actor) {
    val neurons = InteractWithNeurons.as(actor);
    val inputNeuron = neurons.get(0);
    val outputNeuron = neurons.get(1);
    actor.attemptsTo(WatchNeuronOutput.of(outputNeuron));
    reportThat(
        "check that neuron hasn't produced any output yet",
        () -> actor.should(seeThat(new LatestNeuronOutput(), nullValue())));
    actor.attemptsTo(SendSignal.toNeuron(inputNeuron));
    reportThat(
        "sending a signal isn't enough",
        () -> actor.should(seeThat(new LatestNeuronOutput(), nullValue())));
    reportThat(
        "one must tell each neuron to forward their activation",
        () -> {
          inputNeuron.forward();
          outputNeuron.forward();
        });

    actor.attemptsTo(new EnsureTheLatestNeuronOutputIsBetweenZeroAndOne());
    return true;
  }
}
