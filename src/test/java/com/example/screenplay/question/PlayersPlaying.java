package com.example.screenplay.question;

import com.example.GameAdminService;
import com.example.Player;
import com.example.screenplay.ability.ObserveTheGame;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

public class PlayersPlaying extends QuestionWithDefaultSubject<List<Player>> {
  @Override
  public List<Player> answeredBy(Actor actor) {
    GameAdminService service = ObserveTheGame.as(actor).getService();
    return service.getGame().getPlayers();
  }
}
