package com.example.screenplay.action.browser;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PushReposAndStarsToBigquery implements Performable {

  private final Target dependantListItem =
      Target.the("list item showing dependant repository")
          .locatedBy("[data-test-id=\"dg-repo-pkg-dependent\"]");

  @Override
  public <T extends Actor> void performAs(T actor) {
    dependantListItem
        .resolveAllFor(actor)
        .forEach(
            it -> {
              String stars = getRepositoryStars(it);
              if (stars.equals("0")) {
                return;
              }
              String repoName = findRepositoryName(it);
              actor.attemptsTo(new PushRepoAndStarsToBigquery(repoName, Integer.valueOf(stars)));
            });
  }

  private String getRepositoryStars(WebElementFacade item) {
    return item.thenFindAll(By.cssSelector("span")).get(1).getText();
  }

  private String findRepositoryName(WebElementFacade item) {
    return item.find(By.cssSelector("[data-hovercard-type=\"repository\"]")).getAttribute("href");
  }
}
