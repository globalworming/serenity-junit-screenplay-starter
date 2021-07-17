package com.example.e2e.play;

import com.example.E2eBase;
import com.example.screenplay.action.http.AuthenticateSuccessfully;
import com.example.screenplay.action.http.CreateGame;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class GameCreationIT extends E2eBase {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    gameMaster.attemptsTo(new AuthenticateSuccessfully());
  }

  @Test
  public void whenAnAuthenticatedGameMasterCreatesAGame() {
    gameMaster.attemptsTo(new CreateGame());
  }

}
