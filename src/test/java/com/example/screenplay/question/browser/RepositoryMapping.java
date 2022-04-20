package com.example.screenplay.question.browser;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.TableId;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryMapping extends QuestionWithDefaultSubject<Map<String, Integer>> {

  private final Target dependantListItem =
      Target.the("list item showing dependant repository")
          .locatedBy("[data-test-id=\"dg-repo-pkg-dependent\"]");
  Target nextPageButton = Target.the("next page button").locatedBy("//a[contains(text(),'Next')]");

  @Override
  public Map<String, Integer> answeredBy(Actor actor) {
    Map<String, Integer> repositoryToStars = new HashMap<>();

    do {
      List<WebElementFacade> listItem = dependantListItem.resolveAllFor(actor);
      listItem.forEach(
          item -> {
            String repoName = findRepositoryName(item);
            String stars = getRepositoryStars(item);
            repositoryToStars.put(repoName, Integer.valueOf(stars));
            BigQueryOptions.getDefaultInstance()
                .getService()
                .insertAll(
                    InsertAllRequest.newBuilder(TableId.of("github", "dependent projects"))
                        .addRow(
                            InsertAllRequest.RowToInsert.of(
                                Map.of("repo", repoName, "stars", stars)))
                        .build());
          });
      actor.attemptsTo(
          Check.whether(Presence.of(nextPageButton)).andIfSo(Click.on(nextPageButton)));
    } while (Presence.of(nextPageButton).answeredBy(actor));

    return repositoryToStars;
  }

  private String getRepositoryStars(WebElementFacade item) {
    return item.thenFindAll(By.cssSelector("span")).get(1).getText();
  }

  private String findRepositoryName(WebElementFacade item) {
    return item.find(By.cssSelector("[data-hovercard-type=\"repository\"]")).getAttribute("href");
  }
}
