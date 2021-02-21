package com.example.screenplay.action;

import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.actor.Memory;
import com.example.service.GameService;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class CreatesGame implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    GameService service = ManageGames.as(actor);
    String gameName = actor.recall(Memory.GAME_NAME);
    service.createGame(gameName);
  }
}
