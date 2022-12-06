package com.example.screenplay.action.browser;

import com.example.screenplay.ability.AccessBigQuery;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.TableId;
import lombok.val;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.openqa.selenium.By;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PushReposAndStarsToBigquery implements Performable {

  private final Target dependantListItem =
      Target.the("list item showing dependant repository")
          .locatedBy("[data-test-id=\"dg-repo-pkg-dependent\"]");

  @Override
  public <T extends Actor> void performAs(T actor) {
    List<InsertAllRequest.RowToInsert> rows =
        dependantListItem
            .resolveAllFor(actor)
            .map(
                it -> {
                  try {
                    String stars = getRepositoryStars(it);
                    String repoUrl = findRepositoryUrl(it);
                    GHRepository repository;
                    String name = repoUrl.substring("https://github.com/".length());
                    repository = gitHub.getRepository(name);
                    val map = new HashMap<String, Object>();
                    map.put("name", name);
                    map.put("stars", Integer.parseInt(stars));
                    map.put("description", repository.getDescription());
                    map.put("homepage", repository.getHomepage());
                    map.put("topics", repository.listTopics().stream().sorted().collect(Collectors.toList()));
                    return InsertAllRequest.RowToInsert.of(map);
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                });

    AccessBigQuery.as(actor)
        .insertAll(
            InsertAllRequest.newBuilder(TableId.of("github", "dependent projects"))
                .setRows(
                    rows.stream()
                        .filter(it -> !it.getContent().get("stars").equals(0))
                        .collect(Collectors.toList()))
                .build());
  }

  private String getRepositoryStars(WebElementFacade item) {
    return item.thenFindAll(By.cssSelector("span")).get(1).getText();
  }

  private String findRepositoryUrl(WebElementFacade item) {
    return item.find(By.cssSelector("[data-hovercard-type=\"repository\"]")).getAttribute("href");
  }

  private static GitHub gitHub;

  static {
    try {
      gitHub = GitHub.connect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
