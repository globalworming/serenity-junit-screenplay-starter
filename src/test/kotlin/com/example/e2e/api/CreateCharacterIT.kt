package com.example.e2e.api

import com.example.screenplay.action.CreateCharacter
import net.serenitybdd.junit.runners.SerenityRunner
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.rest.abilities.CallAnApi
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence.*
import org.apache.http.HttpStatus.*
import org.hamcrest.CoreMatchers.*
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(SerenityRunner::class)
open class CreateCharacterIT {

  @Test
  fun `when posting`() {
    author.attemptsTo(CreateCharacter())
    author.should(seeThatResponse {
      it.statusCode(SC_OK).body("data.title", `is`("author"))
    })
  }

  companion object {
    private lateinit var author: Actor

    @JvmStatic
    @BeforeClass
    fun setUp() {
      author = Actor("author")
      author.can(CallAnApi.at("https://postman-echo.com/post"))
    }
  }
}