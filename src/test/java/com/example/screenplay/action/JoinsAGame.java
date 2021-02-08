package com.example.screenplay.action;

import com.example.PlayerService;
import com.example.screenplay.ability.PlayAGame;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class JoinsAGame implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    PlayerService service = PlayAGame.as(actor).getService();
    String gameName = actor.recall(Memory.GAME_NAME);
    try {
      service.joinGame(actor.getName(), gameName);
    } catch (UnsupportedOperationException e) {
      actor.remember(Memory.EXCEPTION, e.getMessage());
    }
  }
}