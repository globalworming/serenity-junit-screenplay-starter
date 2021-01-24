package com.example.e2e.game.basicMechanics;

import com.example.GameAdminService;
import com.example.PlayerService;
import com.example.screenplay.ability.ManageGames;
import com.example.screenplay.ability.PlayAGame;
import com.example.screenplay.action.CreatesGame;
import com.example.screenplay.action.JoinsAGame;
import com.example.screenplay.action.PicksAction;
import com.example.screenplay.action.SetupGame;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.GameIsCreated;
import com.example.screenplay.question.PlayersPlaying;
import com.example.screenplay.question.TheyArentAllowedToJoin;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.Cast;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Pending;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.UUID;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SerenityRunner.class)
@Narrative(text = {"As a product owner, I want to develop a game!", "Well, glad that you ask. I imagine a multiplayer online game where two people choose actions in distinct rounds.", "When every player has picked an action, one is declared winner based on certain rules."})
public class GameTest {


  private final Actor admin = new Actor("Admin");
  private final GameAdminService gameAdminService = new GameAdminService();
  private final PlayerService playerService = new PlayerService();
  private final Cast players = Cast.whereEveryoneCan(PlayAGame.through(playerService));
  private final Actor lizzy = players.actorNamed("Lizzy");
  private final Actor alex = players.actorNamed("Alex");
  private final Actor ruby = new Actor("Ruby");

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

  @Test
  public void whenTwoPlayersWantToPlayAGame() {
    lizzy.attemptsTo(new CreatesGame());
    admin.should(seeThat(new GameIsCreated()));
    admin.should(seeThat(new PlayersPlaying(), hasSize(0)));
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    admin.should(seeThat(new PlayersPlaying(), hasSize(2)));
  }

  @Test
  public void whenThreePlayersWantToPlayAGame() {
    admin.attemptsTo(SetupGame.forPlayers(Arrays.asList(lizzy, alex)));
    ruby.attemptsTo(new JoinsAGame());
    ruby.should(seeThat(new TheyArentAllowedToJoin()));
  }

  @Test
  @Pending
  public void whenPlayersPlayTheFirstRoundAndAWinnerIsDeclared() {
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    lizzy.attemptsTo(PicksAction.stone());
    alex.attemptsTo(PicksAction.scissors());
    lizzy.should(seeThat("the winner", actor -> lizzy.getName(), is(lizzy.getName())));
    alex.should(seeThat("the winner", actor -> lizzy.getName(), is(lizzy.getName())));

  }
}
