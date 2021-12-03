package com.example.e2e.rolegate;

import com.example.RolegateBase;
import com.example.screenplay.action.JoinGame;
import com.example.screenplay.action.http.AuthenticateSuccessfully;
import com.example.screenplay.action.http.CreateGame;
import com.example.screenplay.question.LatestCreatedGameName;
import com.example.screenplay.question.NumberOfPlayers;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SerenityRunner.class)
public class GameJoingIT extends RolegateBase {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    gameMaster.attemptsTo(new AuthenticateSuccessfully());
  }

  @Test
  public void whenPlayersJoinCreatedGame() {
    gameMaster.wasAbleTo(new CreateGame());
    String slug = gameMaster.asksFor(new LatestCreatedGameName());
    players.forEach(player -> new JoinGame(slug).performAs(player));
    gameMaster.should(seeThat(new NumberOfPlayers(slug), is(3)));
  }
}
