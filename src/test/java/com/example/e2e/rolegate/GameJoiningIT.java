package com.example.e2e.rolegate;

import com.example.e2e.RolegateBase;
import com.example.screenplay.action.JoinGame;
import com.example.screenplay.action.http.AuthenticateSuccessfully;
import com.example.screenplay.action.http.CreateGame;
import com.example.screenplay.question.rolegate.LatestCreatedGameName;
import com.example.screenplay.question.rolegate.NumberOfPlayers;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SerenityJUnit5Extension.class)
public class GameJoiningIT extends RolegateBase {

  @Override
  @BeforeEach
  public void setUp() {
    super.setUp();
    gameMaster.attemptsTo(new AuthenticateSuccessfully());
  }

  @Test
  @Disabled(
      "this process doesnt work anymore. it may still serve as example how to build steps using a custom webclient")
  public void whenPlayersJoinCreatedGame() {
    gameMaster.wasAbleTo(new CreateGame());
    String slug = gameMaster.asksFor(new LatestCreatedGameName());
    players.forEach(player -> new JoinGame(slug).performAs(player));
    gameMaster.should(seeThat(new NumberOfPlayers(slug), is(3)));
  }
}
