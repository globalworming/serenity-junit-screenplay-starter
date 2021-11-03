package com.example.screenplay.question.browser;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.val;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;
import org.hamcrest.CoreMatchers;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class TheMostLikelyLabelIsOnTop extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    val confidenceValues = Target.the("confidence values").locatedBy(byE2eTestId("inference-results")).resolveAllFor(actor).stream().map(WebElementFacade::getText).collect(Collectors.toList());
    val actualHighestValue = Double.parseDouble(confidenceValues.get(0));
    List<Double> displayedValues = confidenceValues.stream().map(Double::parseDouble).sorted().collect(Collectors.toList());
    val expectedHighestValue = displayedValues.get(displayedValues.size() - 1);
    assertThat(actualHighestValue, CoreMatchers.is(expectedHighestValue));
    return true;
  }

  @NotNull
  private String byE2eTestId(String s) {
    return "//*[@data-e2e-test-id=\"" +
        s +
        "\"]/dd";
  }
}
