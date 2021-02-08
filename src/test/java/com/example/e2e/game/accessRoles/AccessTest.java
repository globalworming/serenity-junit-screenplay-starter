package com.example.e2e.game.accessRoles;


import com.example.E2eBase;
import com.example.screenplay.action.CreatesGame;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class AccessTest extends E2eBase {

  @Test
  public void whenAdminsCreatePlayersCanJoin() {
    admin.attemptsTo(new CreatesGame());
  }
}
