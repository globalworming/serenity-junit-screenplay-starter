package com.example;

import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.ability.PlayAGame;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.Cast;
import org.junit.Before;

import java.util.Arrays;
import java.util.UUID;

public class E2eBase {
  private final GameService gameService = GameService.getInstance();
  private final PlayService playService = new PlayService();

  protected final Actor admin = new Actor("Admin");
  protected final Cast players = Cast.whereEveryoneCan(
      PlayAGame.through(playService),
      ManageGames.through(gameService));
  protected final Actor lizzy = players.actorNamed("Lizzy");
  protected final Actor alex = players.actorNamed("Alex");
  protected final Actor ruby = new Actor("Ruby");

  @Before
  public void setUp() {
    admin.can(ManageGames.through(gameService));
    ruby.can(PlayAGame.through(playService));
    ruby.can(ManageGames.through(gameService));
    String gameName = UUID.randomUUID().toString();
    Arrays.asList(admin, lizzy, ruby, alex)
        .forEach(player -> {
          player.remember(Memory.GAME_NAME, gameName);
        });
  }
}
