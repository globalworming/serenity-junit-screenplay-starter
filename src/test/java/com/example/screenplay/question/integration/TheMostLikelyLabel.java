package com.example.screenplay.question.integration;

import com.example.neuralnet.domain.InferenceResult;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class TheMostLikelyLabel extends QuestionWithDefaultSubject<String> {

  private final int color;

  public static TheMostLikelyLabel of(int i) {
    return new TheMostLikelyLabel(i);
  }

  @Override
  public String answeredBy(Actor actor) {
    val result = new AtomicReference<InferenceResult>();
    Serenity.reportThat(
        actor + " ask for the most likely label",
        () -> result.set(actor.asksFor(TheInferenceResult.forThe(color))));
    String label = result.get().getLabel();
    Serenity.recordReportData().withTitle("label value").andContents(label);
    return label;
  }
}
