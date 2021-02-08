package com.example.screenplay.action;

import com.example.GameService;
import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class JoinsAGame implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    GameService service = ManageGames.as(actor).getService();
    String gameName = actor.recall(Memory.GAME_NAME);
    try {
      service.joinGame(gameName, actor.getName());
    } catch (UnsupportedOperationException e) {
      actor.remember(Memory.EXCEPTION, e.getMessage());
    }
  }
}
