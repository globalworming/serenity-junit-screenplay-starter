package com.example.e2e.neuralnet.gui;

import com.example.neuralnet.component.NeuralNetFactory;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Neuron;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@Narrative(text = "let an actor use a browser. use a neural net to drive decisions")
@RunWith(SerenityRunner.class)
@Slf4j
public class GameIT {

  protected Actor actor = Actor.named("ai");

  @Managed(driver = "chrome")
  WebDriver browser;

  NeuralNet neuralNet = NeuralNetFactory.buildGamePlayingNeuralNet();

  @Before
  public void setUp() {
    actor.can(BrowseTheWeb.with(browser));
    actor.attemptsTo(Open.url("http://localhost:3000"));
  }

  @SneakyThrows
  @Test(timeout = 60000)
  public void play() {
    while (true) {
      for (int i = 0; i < 1000; i++) {
        neuralNet.trainOnFacts();
      }
      feedNeuralNetWithGameState();
      neuralNet.feedForward();
      Neuron neuron =
          neuralNet.getOutputNeurons().stream()
              .reduce((n1, n2) -> n1.getActivation() > n2.getActivation() ? n1 : n2)
              .orElseThrow();
      log.info(neuron.getLabel());
      if (neuron.getLabel().startsWith("click ")) {
        Click.on(neuron.getLabel().substring("click ".length())).performAs(actor);
      }
      if (neuron.getLabel().startsWith("wait")) {
        Thread.sleep(300);
      }
      log.info("current error: " + neuralNet.calculateCurrentError());
      if (neuralNet.calculateCurrentError() < 0.2) {
        return;
      }
    }
  }

  private void feedNeuralNetWithGameState() {
    neuralNet
        .getInputNeurons()
        .forEach(
            neuron -> {
              if ("tile-0 danger".equals(neuron.getLabel())) {
                feedTileDanger(".tile-0", neuron);
              }
              if ("tile-1 danger".equals(neuron.getLabel())) {
                feedTileDanger(".tile-1", neuron);
              }
              if ("tile-2 danger".equals(neuron.getLabel())) {
                feedTileDanger(".tile-2", neuron);
              }
            });
  }

  private void feedTileDanger(String selector, Neuron neuron) {
    val el = Target.the("tile-0").locatedBy(selector).resolveFor(actor);
    if (el.hasClass("danger")) {
      neuron.accept(.5);
      return;
    }
    if (el.hasClass("ultradanger")) {
      neuron.accept(.99);
      return;
    }
    neuron.accept(0);
  }
}
