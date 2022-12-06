package com.example.e2e.browser;

import com.example.screenplay.ability.AccessBigQuery;
import com.example.screenplay.action.browser.ScrapeDependentsWithStars;
import com.example.screenplay.action.browser.SelectCache2kPackage;
import com.example.screenplay.question.bigquery.RepositoryWithTheMostStars;
import com.example.screenplay.question.browser.DoesAnyHaveAStar;
import com.google.cloud.bigquery.BigQueryOptions;
import lombok.SneakyThrows;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SerenityJUnit5Extension.class)
@Narrative(text = "scraping github and persisting in google bigquery")
public class GithubAutomationIT {

  private OnlineCast cast = new OnlineCast();
  private static EnvironmentVariables environmentVariables =
      SystemEnvironmentVariables.createEnvironmentVariables();

  @Test
  @Disabled
  void debug() {
    Actor scraper = cast.actorUsingBrowser("chrome").named("scraper");
    String exampleWithoutStar =
        "https://github.com/serenity-bdd/serenity-core/network/dependents?dependents_before=MjEwMDM5NDA2Nzc&package_id=UGFja2FnZS0xODE2OTAxNzM%3D";
    String exampleWithoutStarButFork =
        "https://github.com/serenity-bdd/serenity-core/network/dependents?dependents_after=MjEwMDQwNDI1NTY&package_id=UGFja2FnZS0xODE2OTAxNzM%3D";

    scraper.attemptsTo(Open.url(exampleWithoutStar));
    scraper.should(seeThat(new DoesAnyHaveAStar(), is(false)));
    scraper.attemptsTo(Open.url(exampleWithoutStarButFork));
    scraper.should(seeThat(new DoesAnyHaveAStar(), is(false)));
  }

  @SneakyThrows
  @Test
  @Disabled
  public void whenFindingMostStars() {
    Actor scraper = cast.actorUsingBrowser("chrome").named("scraper");
    scraper.can(
        AccessBigQuery.with(
            BigQueryOptions.newBuilder()
                .setLocation(" europe-west3")
                .setProjectId("example-13825")
                .build().getService()));
    scraper.attemptsTo(new SelectCache2kPackage());
    scraper.attemptsTo(new ScrapeDependentsWithStars());
    scraper.should(
        seeThat(
            new RepositoryWithTheMostStars(),
            is("https://github.com/Revaldie/QE_Revaldi-Ergiyansyach-Ramadhan")));
  }
}
