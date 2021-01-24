package com.example.screenplay.question;

import com.example.GameAdminService;
import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;

public class GameIsCreated extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    GameAdminService service = ManageGames.as(actor).getService();
    String gameName = actor.recall(Memory.GAME_NAME);
    return service.getGame(gameName) != null;
  }
}
