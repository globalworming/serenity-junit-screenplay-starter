package com.example.screenplay.action.browser;

import com.example.screenplay.ability.AccessBigQuery;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.TableId;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.Map;

public class PushRepoAndStarsToBigquery implements Performable {
  private final String repoName;
  private final Integer stars;

  public PushRepoAndStarsToBigquery(String repoName, Integer stars) {
    this.repoName = repoName;
    this.stars = stars;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    AccessBigQuery.as(actor)
        .insertAll(
            InsertAllRequest.newBuilder(TableId.of("github", "dependent projects"))
                .addRow(InsertAllRequest.RowToInsert.of(Map.of("repo", repoName, "stars", stars)))
                .build());
  }
}
