package com.example.screenplay.question.rolegate;

import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

public class LatestCreatedGameName extends QuestionWithDefaultSubject<String> {
  @Override
  public String answeredBy(Actor actor) {
    return actor.recall(Memory.GAME_SLUG);
  }
}
