package com.example.e2e.browser;

import com.example.screenplay.question.browser.TheMostLikelyLabel;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@Narrative(text = "product that tells blind people the color of something. we want to train a neural net to do that")
@RunWith(SerenityRunner.class)
public class NeuralNetIT {

  Actor actor = Actor.named("tester");
  @Managed(driver = "chrome")
  WebDriver browser;

  @Before
  public void setUp() throws Exception {
    actor.can(BrowseTheWeb.with(browser));
  }

  @Test
  public void actorCanAskNeuralNet() {
    actor.should(seeThat(TheMostLikelyLabel.of("#000000"), is("black")));
    //actor.should(seeThat(TheMostLikelyLabel.of(0xFF), is("white")));
    //actor.should(seeThat(TheMostLikelyLabel.of(0xA0), is("gray")));
  }

  @Test
  public void actorTrainsNeuralNet() {
    //double beforeTraining = actor.asksFor(TheConfidence.of(BLACK));
    //actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    //double afterTraining = actor.asksFor(TheConfidence.of(BLACK));
    //assertThat(beforeTraining, not(is(afterTraining)));
  }

}
