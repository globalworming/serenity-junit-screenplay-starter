package com.example.e2e.game.basicMechanics;

import com.example.GameAdminService;
import com.example.PlayerService;
import com.example.screenplay.ability.ObserveTheGame;
import com.example.screenplay.ability.PlayAGame;
import com.example.screenplay.action.JoinsAGame;
import com.example.screenplay.action.PicksAction;
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
  private final Actor player1 = players.actorNamed("Lizzy");
  private final Actor player2 = players.actorNamed("Alex");
  private final Actor player3 = new Actor("Ruby");

  @Before
  public void setUp() {
    admin.can(ObserveTheGame.through(gameAdminService));
    player3.can(PlayAGame.through(playerService));
  }

  @Test
  public void whenTwoPlayersWantToPlayAGame() {
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    admin.should(seeThat(new GameIsCreated()));
    admin.should(seeThat(new PlayersPlaying(), hasSize(2)));
  }

  @Test
  public void whenThreePlayersWantToPlayAGame() {
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    player3.attemptsTo(new JoinsAGame());
    player3.should(seeThat(new TheyArentAllowedToJoin()));
  }

  @Test
  @Pending
  public void whenPlayersPlayTheFirstRoundAndAWinnerIsDeclared() {
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    player1.attemptsTo(PicksAction.stone());
    player2.attemptsTo(PicksAction.scissors());
    player1.should(seeThat("the winner", actor -> player1.getName(), is(player1.getName())));
    player2.should(seeThat("the winner", actor -> player1.getName(), is(player1.getName())));

  }
}
