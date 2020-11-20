package com.example.e2e.api;

import com.example.screenplay.action.GetAListOfAllPosts;
import com.example.screenplay.action.UploadNewPost;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SerenityRunner.class)
public class JsonPlaceHolderIT {

  private static Actor author;

  @BeforeClass
  public static void setUp() {
    author = new Actor("author");
    author.can(CallAnApi.at("https://jsonplaceholder.typicode.com"));
  }

  @Test
  public void  thereShouldBeMoreThan99Posts() {
    author.attemptsTo(new GetAListOfAllPosts());
    author.should(seeThatResponse("a lot of posts are returned", it -> it
        .statusCode(SC_OK)
        .body("size()", greaterThan(99)))
    );
    Serenity.recordReportData().withTitle("number of posts")
        .andContents(SerenityRest.lastResponse().jsonPath().getList("").size() + "");
  }

  @Test
  public void postSomething() {
    author.attemptsTo(UploadNewPost.containing("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}"));

    author.should(seeThatResponse(response -> response.statusCode(HttpStatus.SC_CREATED)));
    author.should(seeThatResponse(response -> response.body("id", is(101))));
  }
}
