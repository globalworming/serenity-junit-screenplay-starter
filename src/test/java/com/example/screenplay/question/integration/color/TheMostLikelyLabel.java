package com.example.screenplay.question.integration.color;

import com.example.neuralnet.component.HslColor;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;

@RequiredArgsConstructor
public class TheMostLikelyLabel extends QuestionWithDefaultSubject<String> {

  private final HslColor hslColor;

  public static TheMostLikelyLabel of(HslColor hslColor) {
    return new TheMostLikelyLabel(hslColor);
  }

  @Override
  public String answeredBy(Actor actor) {
    return actor.asksFor(TheInferenceResult.forInput(hslColor)).get(0).getLabel();
  }
}
