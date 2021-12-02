package com.example.neuralnet.domain;

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
        "neural nets are made of multiple layers of linked neurons. "
            + "neurons have inputs and corresponding weights. neurons need to calculate and add the inputs "
            + "(they may add a constant bias) and through a sigmoid function produce a value between 0 and 1 as output")
@RunWith(SerenityRunner.class)
public class LinkingNeuronsTest {

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
