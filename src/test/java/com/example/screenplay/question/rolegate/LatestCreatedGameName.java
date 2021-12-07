package com.example.screenplay.question.rolegate;

import com.example.screenplay.action.http.CreateGame;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

public class LatestCreatedGameName extends QuestionWithDefaultSubject<String> {
  @Override
  public String answeredBy(Actor actor) {
    return actor.recall(CreateGame.GAME_SLUG);
  }
}
