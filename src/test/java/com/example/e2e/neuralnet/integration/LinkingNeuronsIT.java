package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.InteractWithNeurons;
import com.example.screenplay.action.integration.LinksTwoNeurons;
import com.example.screenplay.question.integration.ExcitingOneNeuronExcitesTheOther;
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

  private Actor actor = Actor.named("tester");

  @Before
  public void setUp() {
    actor.can(new InteractWithNeurons(new Neuron(), new Neuron()));
  }

  @Test
  public void theOutputOfOneNeuronIsUsedAsInput() {
    actor.attemptsTo(new LinksTwoNeurons());
    actor.should(seeThat(new ExcitingOneNeuronExcitesTheOther()));
  }
}
