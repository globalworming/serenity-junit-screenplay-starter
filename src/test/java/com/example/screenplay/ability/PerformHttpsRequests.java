package com.example.screenplay.ability;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import okhttp3.OkHttpClient;

public class PerformHttpsRequests extends Ability {
  private final OkHttpClient httpClient;

  public PerformHttpsRequests(OkHttpClient httpClient) {

    this.httpClient = httpClient;
  }

  public static PerformHttpsRequests with(OkHttpClient httpClient) {
    return new PerformHttpsRequests(httpClient);
  }

  public static <T extends Actor> OkHttpClient as(T actor) {
    PerformHttpsRequests performHttpsRequests = actor.abilityTo(PerformHttpsRequests.class);
    if (performHttpsRequests == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return performHttpsRequests.httpClient;
  }
}
