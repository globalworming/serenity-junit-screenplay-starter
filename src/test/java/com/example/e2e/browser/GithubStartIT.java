package com.example.e2e.browser;

import com.example.component.Connect;
import com.example.screenplay.action.browser.SelectSerenityCorePackage;
import com.example.screenplay.question.browser.RepositoryMapping;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Narrative(text = "describes the report behavior")
@RunWith(SerenityRunner.class)
public class GithubStartIT {

  private OnlineCast cast = new OnlineCast();
  private static EnvironmentVariables environmentVariables =
      SystemEnvironmentVariables.createEnvironmentVariables();

  @SneakyThrows
  @Test
  public void whenCheckingRepoStars() {
    Actor scraper = cast.actorUsingBrowser("chrome").named("scraper");
    scraper.attemptsTo(new SelectSerenityCorePackage());
    Map<String, Integer> repositoryToStars = scraper.asksFor(new RepositoryMapping());
    assertThat(repositoryToStars.keySet().size(), is(30));

    String githubAccessToken = environmentVariables.getValue("GITHUB_ACCESS_TOKEN");
    assertThat(githubAccessToken, notNullValue());
    GitHub github = new GitHubBuilder().withOAuthToken(githubAccessToken).build();
    Map<String, GHBranch> branches =
        github.getRepository("serenity-bdd/serenity-core").getBranches();
    assertThat(branches.keySet().isEmpty(), is(false));
    Connect.connect();
  }
}
