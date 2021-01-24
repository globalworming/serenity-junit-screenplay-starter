package com.example.screenplay.action;

import com.example.Game;
import com.example.GameAdminService;
import com.example.Player;
import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;

import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SetupGame implements Performable {
  private final List<Actor> players;

  public SetupGame(List<Actor> players) {
    this.players = players;
  }

  public static Performable forPlayers(List<Actor> actors) {
    return instrumented(SetupGame.class, actors);
  }

  @Override
  @Step("{0} sets up game for #players")
  public <T extends Actor> void performAs(T actor) {
    GameAdminService service = ManageGames.as(actor).getService();
    List<Player> players = this.players.stream()
        .map(it -> new Player(it.getName())).collect(Collectors.toList());
    String gameName = actor.recall(Memory.GAME_NAME);
    Game game = Game.builder()
        .players(players)
        .name(gameName)
        .build();
    service.createGame(game);
  }
}
