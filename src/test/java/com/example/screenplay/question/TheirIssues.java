package com.example.screenplay.question;

import com.example.mock.Issue;
import com.example.screenplay.ability.RequestInfrastructure;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

public class TheirIssues extends QuestionWithDefaultSubject<List<Issue>> {
  @Override
  public List<Issue> answeredBy(Actor actor) {
    return RequestInfrastructure.as(actor).getService()
        .getIssues(actor.getName());
  }
}
