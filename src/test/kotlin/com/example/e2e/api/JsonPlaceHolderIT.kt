package com.example.e2e.api

import com.example.screenplay.action.GetListOfAllPosts
import com.example.screenplay.action.UploadNewPost
import com.example.screenplay.question.NumberOfReturnedPosts
import net.serenitybdd.junit.runners.SerenityRunner
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.GivenWhenThen.*
import net.serenitybdd.screenplay.rest.abilities.CallAnApi
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence.*
import net.thucydides.core.annotations.Narrative
import org.apache.http.HttpStatus
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.*
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(SerenityRunner::class)
@Narrative(text = ["a simple REST API example"])
open class JsonPlaceHolderIT {

  @Test
  fun `when there are many posts`() {
    author.attemptsTo(GetListOfAllPosts())
    author.should(seeThat(NumberOfReturnedPosts(), greaterThan(99)))
  }

  @Test
  fun `when posting`() {
    author.attemptsTo(UploadNewPost.containing("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}"))
    author.should(seeThatResponse {
      it.statusCode(HttpStatus.SC_CREATED).body("id", CoreMatchers.`is`(101))
    })
  }

  companion object {
    private lateinit var author: Actor

    @JvmStatic
    @BeforeClass
    fun setUp() {
      author = Actor("author")
      author.can(CallAnApi.at("https://jsonplaceholder.typicode.com"))
    }
  }
}