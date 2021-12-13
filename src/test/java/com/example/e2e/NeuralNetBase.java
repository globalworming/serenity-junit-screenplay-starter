package com.example.e2e;

import com.example.neuralnet.domain.NeuralNet;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import org.junit.Before;

public class NeuralNetBase {

  protected Actor actor = Actor.named("tester");

  @Before
  public void setUp() {
    NeuralNet neuralNet = new NeuralNet();
    actor.can(new InteractWithNeuralNet(neuralNet));
    actor.remember(Memory.NUMBER_OF_TRAINING_ROUNDS, 100);
  }
}
