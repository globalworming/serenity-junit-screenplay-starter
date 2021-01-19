package com.example.screenplay.question;

import com.example.GameAdminService;
import com.example.screenplay.ability.ObserveTheGame;
import net.serenitybdd.screenplay.Actor;

public class GameIsCreated extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    GameAdminService service = ObserveTheGame.as(actor).getService();
    return service.getGame() != null;
  }
}
