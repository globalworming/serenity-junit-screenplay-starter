package com.example.screenplay.question.bigquery;

import com.example.screenplay.ability.AccessBigQuery;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import lombok.SneakyThrows;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.BaseStepListener;
import org.openqa.selenium.By;

public class RepositoryWithTheMostStars extends QuestionWithDefaultSubject<String> {
  @SneakyThrows
  @Override
  public String answeredBy(Actor actor) {
    QueryJobConfiguration queryConfig =
        QueryJobConfiguration.newBuilder(
                "SELECT name, stars FROM `example-13825.github.dependent projects` ORDER BY stars DESC, name ASC LIMIT 1")
            .build();

    TableResult query = AccessBigQuery.as(actor).query(queryConfig);
    return query.iterateAll().iterator().next().get("name").getStringValue();
  }
}
