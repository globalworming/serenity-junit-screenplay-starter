package com.example.e2e.neuralnet.gui;

import com.example.neuralnet.component.NeuralNetFactory;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Neuron;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.webdriver.exceptions.ElementShouldBeEnabledException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

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
    browser.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    actor.can(BrowseTheWeb.with(browser));
    actor.attemptsTo(Open.url("http://localhost:3000"));
    browser.manage().window().setSize(new Dimension(300, 700));
  }

  @SneakyThrows
  @Test(timeout = 600000)
  public void play() {
    while (true) {
      train();
      try {
        restartGameIfNecessary();
        feedNeuralNetWithGameState();
        performRecommendedAction();

      } catch (StaleElementReferenceException
          | NoSuchElementException
          | ElementShouldBeEnabledException ignore) {
        log.error("", ignore);
      }
      if (currentHighScore() >= 100) {
        return;
      }
    }
  }

  private void train() {
    neuralNet.trainOnFacts();
    log.info("current error: " + neuralNet.calculateCurrentError());
  }

  private void restartGameIfNecessary() {
    Target.the("restart button")
        .locatedBy(".do-restart")
        .resolveAllFor(actor)
        .forEach(WebElementFacade::click);
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
              if ("difficulty".equals(neuron.getLabel())) {
                feedDifficulty(neuron);
              }
            });
    neuralNet.feedForward();
  }

  private void performRecommendedAction() {
    Neuron neuron =
        neuralNet.getOutputNeurons().stream()
            .reduce((n1, n2) -> n1.getActivation() > n2.getActivation() ? n1 : n2)
            .orElseThrow();
    log.info(neuron.getLabel());
    if (neuron.getLabel().startsWith("click ")) {
      String selector = neuron.getLabel().substring("click ".length());
      Target.the(selector).locatedBy(selector).resolveFor(actor).click();
    }
  }

  private int currentHighScore() {
    String highscore = Text.of(".high-score").answeredBy(actor);
    if (highscore.length() == 0) {
      return 0;
    }
    return Integer.parseInt(highscore);
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

  private void feedDifficulty(Neuron neuron) {
    String difficulty = Text.of(".see-difficulty").answeredBy(actor);

    if (difficulty.length() == 0) {
      return;
    }
    neuron.accept(Double.parseDouble(difficulty));
  }
}
