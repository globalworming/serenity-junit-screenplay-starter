package com.example.screenplay.action.http;

import com.example.screenplay.action.http.client.Post;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class Authenticate implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    String url = "https://www.rolegate.com/api/v2/api-token-auth";
    String content = "{\n" +
        "\t\"username\": \"test2\",\n" +
        "\t\"password\": \"asdasdasd\"\n" +
        "}";

    actor.attemptsTo(Post.to(url).withBody(content));
  }
}
