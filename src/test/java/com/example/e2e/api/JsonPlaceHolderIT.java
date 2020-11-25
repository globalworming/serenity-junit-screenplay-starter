package com.example.e2e.api;

import com.example.screenplay.action.GetAListOfAllPosts;
import com.example.screenplay.action.UploadNewPost;
import com.example.screenplay.question.NumberOfReturnedPosts;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.annotations.Narrative;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SerenityRunner.class)
@Narrative(title = "title for json placeholder tests", text = "FIXME description")
public class JsonPlaceHolderIT {

  private static Actor author;

  @BeforeClass
  public static void setUp() {
    author = new Actor("author");
    author.can(CallAnApi.at("https://jsonplaceholder.typicode.com"));
  }

  @Test
  public void whenThereAreManyPosts() {
    author.attemptsTo(new GetAListOfAllPosts());
    author.should(seeThat(new NumberOfReturnedPosts(), greaterThan(99)));
  }

  @Test
  public void whenPosting() {
    author.attemptsTo(UploadNewPost.containing("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}"));

    author.should(seeThatResponse(response -> response.statusCode(HttpStatus.SC_CREATED)));
    author.should(seeThatResponse(response -> response.body("id", is(101))));
  }
}
