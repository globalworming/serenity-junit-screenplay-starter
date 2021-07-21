package com.example.screenplay.action

import com.example.screenplay.RequestBodyFactory
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.Performable
import net.serenitybdd.screenplay.rest.interactions.Post
import net.thucydides.core.annotations.Step

open class CreateCharacter : Performable {

  val body ="{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}"
  val body2 ="" +
      "{" +
      "  \"title\":\"foo\"," +
      "  \"body\":\"bar\"," +
      "  \"userId\":1" +
      "}"
  val body3 = """
    {
      "title":"foo",
      "body":"bar",
      "userId":1
    }""".trimIndent()



  @Step("{0} create character containing #jsonBody")
  override fun <T : Actor> performAs(actor: T) {
    val body4 = RequestBodyFactory.buildCreatequestBody2(actor)
    actor.attemptsTo(Post.to("/").with {
      it.header("Content-type", "application/json; charset=UTF-8").body(body4)
    })
  }



}