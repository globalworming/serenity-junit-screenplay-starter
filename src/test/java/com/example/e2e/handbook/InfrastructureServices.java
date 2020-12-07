package com.example.e2e.handbook;

import com.example.mock.InfrastructureService;
import com.example.mock.Issue;
import com.example.mock.OpsManagement;
import com.example.screenplay.ProvideInfrastructure;
import com.example.screenplay.ability.ManageInfrastructure;
import com.example.screenplay.ability.RequestInfrastructure;
import com.example.screenplay.action.DeployTheirApplication;
import com.example.screenplay.action.RequestServer;
import com.example.screenplay.question.ListOfOpenIssues;
import com.example.screenplay.question.RequestingTheIndexHtmlReturnsSomething;
import com.example.screenplay.question.TheirIssues;
import com.example.screenplay.question.TheirIssuesAreResolved;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(SerenityRunner.class)
public class InfrastructureServices {

  private Actor developer;
  private Actor operations;

  @Before
  public void setUp() {
    developer = new Actor("Jill Liebon");
    developer.can(RequestInfrastructure.with(InfrastructureService.getInstance()));

    operations = new Actor("Hektor Olson");
    operations.can(ManageInfrastructure.with(OpsManagement.getInstance()));
  }

  @Test
  public void whenDeployingANewApplication() {
    developer.attemptsTo(new RequestServer());
    operations.should(seeThat(
        new ListOfOpenIssues(),
        hasItem(Issue.by(developer.getName()))
    ));
    operations.attemptsTo(ProvideInfrastructure.forDeveloper(developer.getName()));
    developer.should(seeThat(new TheirIssuesAreResolved()));
    List<Issue> issues = developer.asksFor(new TheirIssues());
    developer.should(seeThat(
        "issue description",
        a -> issues.get(0).getDescription(),
        containsString("intra3.example.com")));

    developer.attemptsTo(new DeployTheirApplication());
    developer.should(seeThat(new RequestingTheIndexHtmlReturnsSomething()));
  }
}
