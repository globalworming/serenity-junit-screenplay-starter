package com.example.screenplay.question;

import com.example.PlayerService;
import com.example.screenplay.ability.PlayAGame;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;

public class TheWinnerIs extends QuestionWithDefaultSubject<String> {
  @Override
  public String answeredBy(Actor actor) {
    PlayerService service = PlayAGame.as(actor).getService();
    String gameName = actor.recall(Memory.GAME_NAME);
    return service.getWinner(gameName);
  }
}
