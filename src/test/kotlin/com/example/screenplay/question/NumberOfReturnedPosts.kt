package com.example.screenplay.question

import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.rest.questions.LastResponse

class NumberOfReturnedPosts : QuestionWithDefaultSubject<Int>() {
  override fun answeredBy(actor: Actor): Int {
    return actor.asksFor(LastResponse()).jsonPath().getList<Any>("").size
  }
}