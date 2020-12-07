package com.example.screenplay.question;

import com.example.mock.Issue;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

public class TheirIssuesAreResolved extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    List<Issue> issues = actor.asksFor(new TheirIssues());
    return issues.stream().allMatch(Issue::isDone);
  }
}
