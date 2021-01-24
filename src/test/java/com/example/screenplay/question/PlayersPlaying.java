package com.example.screenplay.question;

import com.example.GameAdminService;
import com.example.Player;
import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

public class PlayersPlaying extends QuestionWithDefaultSubject<List<Player>> {
  @Override
  public List<Player> answeredBy(Actor actor) {
    GameAdminService service = ManageGames.as(actor).getService();
    String gameName = actor.recall(Memory.GAME_NAME);
    return service.getGame(gameName).getPlayers();
  }
}
