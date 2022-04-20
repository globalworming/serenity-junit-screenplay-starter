package com.example.e2e.browser;

import com.example.screenplay.ability.AccessBigQuery;
import com.example.screenplay.action.browser.ScrapeDependentsWithStars;
import com.example.screenplay.action.browser.SelectSerenityCorePackage;
import com.google.cloud.bigquery.BigQueryOptions;
import lombok.SneakyThrows;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class GithubAutomationIT {

  private OnlineCast cast = new OnlineCast();
  private static EnvironmentVariables environmentVariables =
      SystemEnvironmentVariables.createEnvironmentVariables();

  @SneakyThrows
  @Disabled
  @Test
  public void whenFindingMostStars() {
    Actor scraper = cast.actorUsingBrowser("chrome").named("scraper");
    scraper.can(AccessBigQuery.with(BigQueryOptions.getDefaultInstance().getService()));
    scraper.attemptsTo(new SelectSerenityCorePackage());
    scraper.attemptsTo(new ScrapeDependentsWithStars());
    // String s = scraper.asksFor(new RepositoryWithTheMostStars());

    // String githubAccessToken = environmentVariables.getValue("GITHUB_ACCESS_TOKEN");
    // assertThat(githubAccessToken, notNullValue());
    // GitHub github = new GitHubBuilder().withOAuthToken(githubAccessToken).build();
    // Map<String, GHBranch> branches =
    //    github.getRepository("serenity-bdd/serenity-core").getBranches();
    // assertThat(branches.keySet().isEmpty(), is(false));
  }
}
