package com.example.screenplay.action

import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.Performable
import net.serenitybdd.screenplay.Tasks.*
import net.serenitybdd.screenplay.rest.interactions.Post
import net.thucydides.core.annotations.Step

open class UploadNewPost(private val jsonBody: String) : Performable {

  @Step("{0} uploads new post containing #jsonBody")
  override fun <T : Actor> performAs(actor: T) {
    actor.attemptsTo(Post.to("/posts").with {
      it.header("Content-type", "application/json; charset=UTF-8").body(jsonBody)
    })
  }

  companion object {
    fun containing(jsonBody: String): UploadNewPost {
      return instrumented(UploadNewPost::class.java, jsonBody)
    }
  }
}