package com.example;

import com.example.screenplay.ability.PerformHttpsRequests;
import com.example.screenplay.actor.Memory;
import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.util.Collections;
import java.util.List;

public class E2eBase {
  protected Actor gameMaster;
  protected List<Actor> players;

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  @Before
  public void setUp() {
    gameMaster = buildGameMaster();
    players = buildPlayers();
  }

  private static Actor buildGameMaster() {
    Actor gameMaster = new Actor("game master");
    OkHttpClient httpClient = new OkHttpClient();
    gameMaster.can(PerformHttpsRequests.with(httpClient));
    gameMaster.remember(Memory.USERNAME, "test2");
    gameMaster.remember(Memory.PASSWORD, "asdasdasd");

    return gameMaster;

  }

  private List<Actor> buildPlayers() {
    FunnyName faker = new Faker().funnyName();
    Actor actor = new Actor(faker.name());
    actor.can(BrowseTheWeb.with(aBrowser));
    actor.remember(Memory.USERNAME, "test4");
    actor.remember(Memory.PASSWORD, "asdasdasd");
    return Collections.singletonList(actor);
  }
}
