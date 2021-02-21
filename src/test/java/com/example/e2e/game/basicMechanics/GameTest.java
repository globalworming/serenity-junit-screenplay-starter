package com.example.e2e.game.basicMechanics;

import com.example.screenplay.action.CreatesGame;
import com.example.screenplay.action.JoinsAGame;
import com.example.screenplay.action.PicksAction;
import com.example.screenplay.action.SetupGame;
import com.example.screenplay.question.GameIsCreated;
import com.example.screenplay.question.PlayersPlaying;
import com.example.screenplay.question.TheWinnerIs;
import com.example.screenplay.question.TheyArentAllowedToJoin;
import com.example.service.E2eBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SerenityRunner.class)
@Narrative(
    text = {
      "As a product owner, I want to develop a game!",
      "Well, glad that you ask. I imagine a multiplayer online game where two people choose actions in distinct rounds.",
      "When every player has picked an action, one is declared winner based on certain rules."
    })
public class GameTest extends E2eBase {

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
    lizzy.attemptsTo(new CreatesGame());
    players.getActors().forEach(it -> it.attemptsTo(new JoinsAGame()));
    ruby.attemptsTo(new JoinsAGame());
    ruby.should(seeThat(new TheyArentAllowedToJoin()));
  }

  @Test
  public void whenPlayersPlayTheFirstRoundAndAWinnerIsDeclared() {
    admin.attemptsTo(SetupGame.forPlayers(Arrays.asList(lizzy, alex)));
    lizzy.attemptsTo(PicksAction.stone());
    alex.attemptsTo(PicksAction.paper());
    lizzy.should(seeThat(new TheWinnerIs(), is(alex.getName())));
    alex.should(seeThat(new TheWinnerIs(), is(alex.getName())));
  }
}
