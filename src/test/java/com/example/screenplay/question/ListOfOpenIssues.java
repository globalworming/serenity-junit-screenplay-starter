package com.example.screenplay.question;

import com.example.mock.Issue;
import com.example.screenplay.ability.ManageInfrastructure;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

public class ListOfOpenIssues extends QuestionWithDefaultSubject<List<Issue>>{
  @Override
  public List<Issue> answeredBy(Actor actor) {
    return ManageInfrastructure.as(actor).getService().getIssues();
  }
}
