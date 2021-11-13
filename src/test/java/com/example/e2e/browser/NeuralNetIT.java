package com.example.e2e.browser;

import com.example.screenplay.action.browser.TrainNeuralNet;
import com.example.screenplay.domain.ColorSet;
import com.example.screenplay.question.browser.TheConfidence;
import com.example.screenplay.question.browser.TheMostLikelyLabel;
import com.example.screenplay.question.browser.TheMostLikelyLabelIsOnTop;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Narrative(
    text =
        "product that tells blind people the color of something. we want to train a neural net to do that")
@RunWith(SerenityRunner.class)
public class NeuralNetIT {

  Actor actor = Actor.named("tester");

  @Managed(driver = "chrome")
  WebDriver browser;

  @Before
  public void setUp() throws Exception {
    actor.can(BrowseTheWeb.with(browser));
    actor.attemptsTo(Open.url("http://localhost:3000"));
  }

  @Test
  public void actorCanAskNeuralNet() {
    actor.should(seeThat(TheMostLikelyLabel.of("#000000"), is("black")));
    // actor.should(seeThat(TheMostLikelyLabel.of(0xFF), is("white")));
    // actor.should(seeThat(TheMostLikelyLabel.of(0xA0), is("gray")));
  }

  @Test
  public void actorTrainsNeuralNet() {
    val trainingData = List.of(ColorSet.builder().color("#000000").label("black").build());
    double beforeTraining = actor.asksFor(TheConfidence.of("black"));
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    double afterTraining = actor.asksFor(TheConfidence.of("black"));
    assertThat(beforeTraining, not(is(afterTraining)));
  }

  @Test
  public void theActorSeesTheMostLikelyLabelOnTop() {
    actor.attemptsTo(Open.url("http://localhost:3000"));
    actor.should(eventually(seeThat(new TheMostLikelyLabelIsOnTop())));
  }
}
