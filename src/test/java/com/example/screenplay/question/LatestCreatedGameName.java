package com.example.screenplay.question;

import com.example.screenplay.action.http.CreateGame;
import net.serenitybdd.screenplay.Actor;

public class LatestCreatedGameName extends QuestionWithDefaultSubject<String> {
  @Override
  public String answeredBy(Actor actor) {
    return actor.recall(CreateGame.GAME_SLUG);
  }
}
