package com.example.screenplay.question;

import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.actor.Memory;
import com.example.service.GameService;
import net.serenitybdd.screenplay.Actor;

public class GameIsCreated extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    GameService service = ManageGames.as(actor);
    String gameName = actor.recall(Memory.GAME_NAME);
    return service.getGame(gameName) != null;
  }
}
