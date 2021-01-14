package com.example.screenplay.action;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UploadNewPost implements Performable {
  private final String jsonBody;

  public UploadNewPost(String jsonBody) {
    this.jsonBody = jsonBody;
  }

  @Override
  @Step("{0} uploads new post containing #jsonBody")
  public <T extends Actor> void performAs(T t) {
    t.attemptsTo(Post.to("/posts").with(request -> {
          request.header("Content-type", "application/json; charset=UTF-8");
          return request.body(jsonBody);
        }
    ));
  }

  public static UploadNewPost containing(String jsonBody) {
    return instrumented(UploadNewPost.class, jsonBody);
  }
}
