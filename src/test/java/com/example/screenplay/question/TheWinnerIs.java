package com.example.screenplay.question;

import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.actor.Memory;
import com.example.service.GameService;
import net.serenitybdd.screenplay.Actor;

public class TheWinnerIs extends QuestionWithDefaultSubject<String> {
  @Override
  public String answeredBy(Actor actor) {
    GameService service = ManageGames.as(actor);
    String gameName = actor.recall(Memory.GAME_NAME);
    return service.getWinner(gameName);
  }
}
