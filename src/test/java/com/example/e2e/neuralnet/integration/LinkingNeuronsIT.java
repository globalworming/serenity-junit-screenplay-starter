package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.InteractWithNeurons;
import com.example.screenplay.action.LinksTwoNeurons;
import com.example.screenplay.question.ExcitingOneNeuronExcitesTheOther;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@Narrative(
    text =
        "when linking two neurons the activation of one neuron activates the other. works only in one direction though there are neural networks which allow for loops or neurons activating themselves")
@RunWith(SerenityRunner.class)
public class LinkingNeuronsIT {

  private Neuron inputNeuron;
  private Neuron outputNeuron;
  private Actor actor = Actor.named("tester");

  @Before
  public void setUp() {
    inputNeuron = new Neuron();
    outputNeuron = new Neuron();
    actor.can(new InteractWithNeurons(inputNeuron, outputNeuron));
  }

  @Test
  public void theOutputOfOneNeuronIsUsedAsInput() {
    actor.attemptsTo(new LinksTwoNeurons());
    actor.should(seeThat(new ExcitingOneNeuronExcitesTheOther()));
  }
}
