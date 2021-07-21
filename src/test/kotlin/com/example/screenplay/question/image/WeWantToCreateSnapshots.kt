package com.example.screenplay.question.image

import com.example.screenplay.question.QuestionWithDefaultSubject
import net.serenitybdd.screenplay.Actor
import net.thucydides.core.util.SystemEnvironmentVariables

class WeWantToCreateSnapshots : QuestionWithDefaultSubject<Boolean>() {

  override fun answeredBy(actor: Actor): Boolean {
    return SystemEnvironmentVariables.createEnvironmentVariables().getPropertyAsBoolean("ashot.image.comparison.create.snapshots", false)
  }

}
