package com.example.screenplay.question;

import com.example.screenplay.ability.InteractWithNeurons;
import com.example.screenplay.action.EnsureTheLatestNeuronOutputIsBetweenZeroAndOne;
import com.example.screenplay.action.SendSignal;
import com.example.screenplay.action.WatchNeuronOutput;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.nullValue;

public class ExcitingOneNeuronExcitesTheOther extends QuestionWithDefaultSubject<Boolean> {

  @Override
  public Boolean answeredBy(Actor actor) {
    val neurons = InteractWithNeurons.as(actor);
    val inputNeuron = neurons.get(0);
    val outputNeuron = neurons.get(1);
    actor.attemptsTo(WatchNeuronOutput.of(outputNeuron));
    Serenity.reportThat(
        "check that neuron hasn't produced any output yet",
        () -> actor.should(seeThat(new LatestNeuronOutput(), nullValue())));
    actor.attemptsTo(SendSignal.toNeuron(inputNeuron));
    actor.attemptsTo(new EnsureTheLatestNeuronOutputIsBetweenZeroAndOne());
    return true;
  }
}
