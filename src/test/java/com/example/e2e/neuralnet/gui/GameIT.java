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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Narrative(text = "let an actor use a browser. use a neural net to drive decisions")
@RunWith(SerenityRunner.class)
@Slf4j
public class GameIT {

  protected Actor actor = Actor.named("ai");

  @Managed(driver = "chrome")
  WebDriver browser;

  NeuralNet neuralNet = NeuralNetFactory.buildGamePlayingNeuralNetThatTrainedSomeRounds();

  @Before
  public void setUp() {
    browser.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    actor.can(BrowseTheWeb.with(browser));
    actor.attemptsTo(Open.url("http://localhost:3000"));
    browser.manage().window().setSize(new Dimension(300, 700));
  }

  @SneakyThrows
  @Test()
  public void play() {
    while (true) {
      try {
        restartGameIfNecessary();
        feedNeuralNetWithGameState();
        performRecommendedActions();
      } catch (StaleElementReferenceException
          | NoSuchElementException
          | ElementShouldBeEnabledException ignore) {
      }
      if (currentHighScore() >= 5) {
        return;
      }
      train();
    }
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
    val inputs =
        neuralNet.getInputNeurons().stream()
            .map(it -> it.getSignals().get(0))
            .collect(Collectors.toList());

    warnIfNoFactForThat();
    neuralNet.feedForward();
  }

  private void performRecommendedActions() {
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
    String highscore = Text.of(".see-highscore").answeredBy(actor);
    if (highscore.length() == 0) {
      return 0;
    }
    return Integer.parseInt(highscore);
  }

  private void train() {
    for (int i = 0; i < 100; i++) {
      neuralNet.trainOnFacts();
    }
    log.info("current error: " + neuralNet.calculateCurrentError());
  }

  private void feedTileDanger(String selector, Neuron neuron) {
    val el = Target.the("tile-0").locatedBy(selector).resolveFor(actor);
    if (el.hasClass("danger")) {
      neuron.accept(.5);
      return;
    }
    if (el.hasClass("ultradanger")) {
      neuron.accept(1.);
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

  private void warnIfNoFactForThat() {
    List<Double> inputs =
        neuralNet.getInputNeurons().stream()
            .map(inputNeuron -> inputNeuron.getSignals().get(0))
            .collect(Collectors.toList());
    if (neuralNet.getFacts().stream()
        .filter(
            fact -> {
              boolean factMatchesInputPresent = true;
              for (int i = 0; i < inputs.size(); i++) {
                factMatchesInputPresent &= inputs.get(i).equals(fact.getInputs().get(i));
              }
              return factMatchesInputPresent;
            })
        .findFirst()
        .isEmpty()) {
      log.warn("no fact for inputs {}", inputs);
    }
  }
}
