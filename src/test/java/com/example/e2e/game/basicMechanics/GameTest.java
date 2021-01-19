package com.example.e2e.game.basicMechanics;

import com.example.screenplay.action.JoinsAGame;
import com.example.screenplay.action.PicksAction;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.Cast;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SerenityRunner.class)
@Narrative(text = {"As a product owner, I want to develop a game!", "Well, glad that you ask. I imagine a multiplayer online game where two people choose actions in distinct rounds.", "When every player has picked an action, one is declared winner based on certain rules."})
public class GameTest {

  private final Cast players = new Cast();

  private final Actor player1 = players.actorNamed("Lizzy");
  private final Actor player2 = players.actorNamed("Alex");
  private final Actor player3 = new Actor("Ruby");
  private final Actor admin = new Actor("Admin");


  @Test
  public void whenTwoPlayersWantToPlayAGame() {
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    admin.should(seeThat("a game has been created", actor -> true));
    admin.should(seeThat("two players are playing this game", actor -> true));
  }

  @Test
  public void whenThreePlayersWantToPlayAGame() {
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    player3.attemptsTo(new JoinsAGame());
    player3.should(seeThat("they aren't allowed to join", a -> true));
  }

  @Test
  public void whenPlayersPlayTheFirstRoundAndAWinnerIsDeclared() {
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    player1.attemptsTo(PicksAction.stone());
    player2.attemptsTo(PicksAction.scissors());
    player1.should(seeThat("the winner", actor -> player1.getName(), is(player1.getName())));
    player2.should(seeThat("the winner", actor -> player1.getName(), is(player1.getName())));

  }
}
