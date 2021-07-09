package com.example.e2e.auth;

import com.example.screenplay.ability.PerformHttpsRequests;
import com.example.screenplay.action.http.Authenticate;
import com.example.screenplay.action.http.CreateGame;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.questions.TheMemory;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@RunWith(SerenityRunner.class)
public class GameCreationIT {

  Actor gameMaster;

  @Before
  public void setUp() {
    gameMaster = new Actor("game master");
    OkHttpClient httpClient = new OkHttpClient();
    gameMaster.can(PerformHttpsRequests.with(httpClient));
    gameMaster.remember(Memory.USERNAME, "test2");
    gameMaster.remember(Memory.PASSWORD, "asdasdasd");

    gameMaster.attemptsTo(new Authenticate());
    gameMaster.should(seeThat(TheMemory.withKey(Memory.ACCESS_TOKEN).isPresent()));
  }

  @Test
  public void whenCreatingAGame() {
    gameMaster.attemptsTo(new CreateGame());

  }
}
