package com.example.screenplay.question;

import com.example.model.Player;
import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.actor.Memory;
import com.example.service.GameService;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

public class PlayersPlaying extends QuestionWithDefaultSubject<List<Player>> {
  @Override
  public List<Player> answeredBy(Actor actor) {
    GameService service = ManageGames.as(actor);
    String gameName = actor.recall(Memory.GAME_NAME);
    return service.getGame(gameName).getPlayers();
  }
}
