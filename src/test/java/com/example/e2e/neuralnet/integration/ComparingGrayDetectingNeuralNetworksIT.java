package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.component.CustomLabelGrayFromLightnessNeuralNet;
import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.AskAndTrainColorDetectingNeuralNetwork;
import com.example.screenplay.action.WireAllTheNeurons;
import com.example.screenplay.action.integration.AddFactsSoGrayResemblesCurveWithPeakAtHalfLightness;
import com.example.screenplay.action.integration.TrainNeuralNetForManyRounds;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.integration.CurrentError;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.number.IsCloseTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.String.format;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@Narrative(
    text = {
      "Just given a hsl-color lightness, labeling some color as 'gray' isn't trivial though it's basically something close to a quadratic function like f(x)=-4x^2+4x. So a low lightness would be black, not gray, a high lightness be white, not gray. But a medium lightness is very likely to be labelled gray.",
      "Turns out you have a hard time doing this with just Sigmoid neurons, what you want are ReLU neurons, see https://www.google.com/search?q=relu+networks+are+universal+approximators+via+piecewise+linear+or+constant+functions. So let's compare a few setups and their training effectiveness, like: how small does the error get after a given amount of training rounds. (TODO training time?)"
    })
@RunWith(SerenityRunner.class)
public class ComparingGrayDetectingNeuralNetworksIT {

  Actor actor = Actor.named("tester");
  int rounds = 100000;
  CustomLabelGrayFromLightnessNeuralNet neuralNet = new CustomLabelGrayFromLightnessNeuralNet();

  @Before
  public void setUp() {
    actor.can(new AskAndTrainColorDetectingNeuralNetwork(neuralNet));
    actor.remember(Memory.NUMBER_OF_TRAINING_ROUNDS, rounds);
    actor.attemptsTo(new AddFactsSoGrayResemblesCurveWithPeakAtHalfLightness());
  }

  /** the default build, only one sigmoid in- and one output */
  @Test
  public void noHiddenNeurons() {
    actor.attemptsTo(new WireAllTheNeurons());
    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    Serenity.reportThat(
        format(
            "after %s training rounds: current error<%s> should still be close to inital error<%s>. this network can't train efficiently compared to others",
            rounds, errorAfterTraining, errorBeforeTraining),
        () ->
            actor.should(seeThat(new CurrentError(), IsCloseTo.closeTo(errorBeforeTraining, .1))));
  }

  /** the default build, only one sigmoid in- and one output */
  @Test
  public void onHiddenLayerOfThreeReluNeurons() {
    Serenity.reportThat(
        "set up 3 hidden ReLU neurons",
        () -> {
          neuralNet.addNeuronToLayer(new Neuron(ActivationFunction.ReLU), 1);
          actor.attemptsTo(new WireAllTheNeurons());
        });

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    Serenity.reportThat(
        format(
            "after %s training rounds: current error<%s> should still be close to inital error<%s>. this network can't train efficiently compared to others",
            rounds, errorAfterTraining, errorBeforeTraining),
        () ->
            actor.should(seeThat(new CurrentError(), IsCloseTo.closeTo(errorBeforeTraining, .1))));
  }
}
