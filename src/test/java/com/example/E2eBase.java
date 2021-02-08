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
  private final GameAdminService gameAdminService = new GameAdminService();
  private final PlayerService playerService = new PlayerService();

  protected final Actor admin = new Actor("Admin");
  protected final Cast players = Cast.whereEveryoneCan(PlayAGame.through(playerService));
  protected final Actor lizzy = players.actorNamed("Lizzy");
  protected final Actor alex = players.actorNamed("Alex");
  protected final Actor ruby = new Actor("Ruby");

  @Before
  public void setUp() {
    admin.can(ManageGames.through(gameAdminService));
    ruby.can(PlayAGame.through(playerService));
    String gameName = UUID.randomUUID().toString();
    Arrays.asList(admin, lizzy, ruby, alex)
        .forEach(player -> {
          player.remember(Memory.GAME_NAME, gameName);
        });
  }
}
