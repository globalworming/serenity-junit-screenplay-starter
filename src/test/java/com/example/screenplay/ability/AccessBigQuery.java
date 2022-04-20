package com.example.screenplay.ability;

import com.google.cloud.bigquery.BigQuery;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;

public class AccessBigQuery extends Ability {
  private final BigQuery bigQuery;

  public AccessBigQuery(BigQuery bigQuery) {

    this.bigQuery = bigQuery;
  }

  public static AccessBigQuery with(BigQuery bigQuery) {
    return new AccessBigQuery(bigQuery);
  }

  public static <T extends Actor> BigQuery as(T actor) {
    AccessBigQuery accessBigQuery = actor.abilityTo(AccessBigQuery.class);
    if (accessBigQuery == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return accessBigQuery.bigQuery;
  }
}
