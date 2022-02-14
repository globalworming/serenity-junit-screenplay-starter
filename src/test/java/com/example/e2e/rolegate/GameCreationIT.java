package com.example.e2e.rolegate;

import com.example.e2e.RolegateBase;
import com.example.screenplay.action.http.AuthenticateSuccessfully;
import com.example.screenplay.action.http.CreateGame;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class GameCreationIT extends RolegateBase {

  @Override
  @BeforeEach
  public void setUp() {
    super.setUp();
    gameMaster.attemptsTo(new AuthenticateSuccessfully());
  }

  @Test
  @Disabled(
      "this process doesnt work anymore. it may still serve as example how to build steps using a custom webclient")
  public void whenAnAuthenticatedGameMasterCreatesAGame() {
    gameMaster.attemptsTo(new CreateGame());
  }
}
