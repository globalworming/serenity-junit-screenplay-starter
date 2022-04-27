package com.example.e2e.browser;

import com.example.screenplay.ability.AccessBigQuery;
import com.example.screenplay.action.browser.ScrapeDependentsWithStars;
import com.example.screenplay.action.browser.SelectSerenityCorePackage;
import com.example.screenplay.question.bigquery.RepositoryWithTheMostStars;
import com.example.screenplay.question.browser.DoesAnyHaveAStar;
import com.google.cloud.bigquery.BigQueryOptions;
import lombok.SneakyThrows;
import lombok.val;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SerenityJUnit5Extension.class)
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

  @Test
  void debug2() {
    val actor = new Actor("scraper");
    actor.can(AccessBigQuery.with(BigQueryOptions.getDefaultInstance().getService()));
    actor.should(
        seeThat(
            new RepositoryWithTheMostStars(),
            is("https://github.com/Revaldie/QE_Revaldi-Ergiyansyach-Ramadhan")));
  }

  @SneakyThrows
  @Test
  @Disabled
  public void whenFindingMostStars() {
    Actor scraper = cast.actorUsingBrowser("chrome").named("scraper");
    scraper.can(AccessBigQuery.with(BigQueryOptions.getDefaultInstance().getService()));
    scraper.attemptsTo(new SelectSerenityCorePackage());
    scraper.attemptsTo(new ScrapeDependentsWithStars());
    scraper.should(
        seeThat(
            new RepositoryWithTheMostStars(),
            is("https://github.com/Revaldie/QE_Revaldi-Ergiyansyach-Ramadhan")));
    // String s = scraper.asksFor(new RepositoryWithTheMostStars());

    // String githubAccessToken = environmentVariables.getValue("GITHUB_ACCESS_TOKEN");
    // assertThat(githubAccessToken, notNullValue());
    // GitHub github = new GitHubBuilder().withOAuthToken(githubAccessToken).build();
    // Map<String, GHBranch> branches =
    //    github.getRepository("serenity-bdd/serenity-core").getBranches();
    // assertThat(branches.keySet().isEmpty(), is(false));
  }
}
