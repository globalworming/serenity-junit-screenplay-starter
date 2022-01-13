package com.example.e2e;

import com.example.neuralnet.component.LabelHslColorNeuralNet;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.NeuralNetTrainer;
import com.example.screenplay.ability.InteractWithColorDetectingNeuralNet;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.ability.TrainColorDetectingNeuralNetwork;
import com.example.screenplay.ability.TrainNeuralNetwork;
import com.example.screenplay.actor.Memory;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class NeuralNetBase {

  protected Actor you = Actor.named("you");
  protected Actor trainer = Actor.named("trainer");

  @Managed(driver = "chrome")
  WebDriver browser;

  @Managed(driver = "chrome")
  WebDriver otherBrowser;

  @Before
  public void setUp() {
    trainer.remember(Memory.DEFAULT_NUMBER_OF_TRAINING_ROUNDS, 400);

    NeuralNet neuralNet = new NeuralNet();
    you.can(BrowseTheWeb.with(browser));
    trainer.can(BrowseTheWeb.with(otherBrowser));

    you.can(new InteractWithNeuralNet(neuralNet));
    trainer.can(new TrainNeuralNetwork(NeuralNetTrainer.builder().neuralNet(neuralNet).build()));

    val labelHslColorNeuralNet = new LabelHslColorNeuralNet();
    you.can(new InteractWithColorDetectingNeuralNet(labelHslColorNeuralNet));
    trainer.can(
        new TrainColorDetectingNeuralNetwork(
            NeuralNetTrainer.builder().neuralNet(labelHslColorNeuralNet).build()));
  }
}
