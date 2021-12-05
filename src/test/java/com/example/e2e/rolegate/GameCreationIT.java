package com.example.e2e.rolegate;

import com.example.e2e.RolegateBase;
import com.example.screenplay.action.http.AuthenticateSuccessfully;
import com.example.screenplay.action.http.CreateGame;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class GameCreationIT extends RolegateBase {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    gameMaster.attemptsTo(new AuthenticateSuccessfully());
  }

  @Test
  @Ignore(
      "this process doesnt work anymore. it may still serve as example how to build steps using a custom webclient")
  public void whenAnAuthenticatedGameMasterCreatesAGame() {
    gameMaster.attemptsTo(new CreateGame());
  }
}
