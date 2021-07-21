package com.example.screenplay.action

import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.Performable
import net.serenitybdd.screenplay.rest.interactions.Get

open class GetListOfAllPosts : Performable {
  override fun <T : Actor> performAs(actor: T) {
    actor.attemptsTo(Get.resource("/posts"))
  }
}