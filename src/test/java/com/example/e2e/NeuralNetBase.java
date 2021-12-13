package com.example.e2e;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import com.example.neuralnet.domain.NeuralNet;
import com.example.screenplay.ability.AskAndTrainColorDetectingNeuralNetwork;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class NeuralNetBase {

  protected Actor actor = Actor.named("tester");

  @Managed(driver = "chrome")
  WebDriver browser;

  // TODO split up into different actors with different abilities
  @Before
  public void setUp() {
    NeuralNet neuralNet = new NeuralNet();
    actor.can(new InteractWithNeuralNet(neuralNet));
    actor.remember(Memory.NUMBER_OF_TRAINING_ROUNDS, 100);
    actor.can(BrowseTheWeb.with(browser));
    actor.can(new AskAndTrainColorDetectingNeuralNetwork(new ColorDetectingNeuralNetwork()));
  }
}
