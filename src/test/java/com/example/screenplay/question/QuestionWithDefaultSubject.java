package com.example.screenplay.question;

import net.serenitybdd.screenplay.Question;

public abstract class QuestionWithDefaultSubject<T> implements Question<T> {

  @Override
  public String getSubject() {
    return this.getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2");
  }

  @Override
  public String toString() {
    return getSubject();
  }
}