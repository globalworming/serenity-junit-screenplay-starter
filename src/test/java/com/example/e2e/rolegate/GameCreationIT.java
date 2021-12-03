package com.example.e2e.rolegate;

import com.example.e2e.RolegateBase;
import com.example.screenplay.action.http.AuthenticateSuccessfully;
import com.example.screenplay.action.http.CreateGame;
import org.junit.Before;
import org.junit.Test;

public class GameCreationIT extends RolegateBase {

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
