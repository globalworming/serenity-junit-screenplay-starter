package com.example.screenplay.question.browser;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.questions.Presence;

import static com.example.screenplay.page.GithubDependendsPage.NEXT_PAGE_BUTTON;

public class HasNextPage extends QuestionWithDefaultSubject<Boolean> {

  @Override
  public Boolean answeredBy(Actor actor) {
    return Presence.of(NEXT_PAGE_BUTTON).answeredBy(actor);
  }
}
