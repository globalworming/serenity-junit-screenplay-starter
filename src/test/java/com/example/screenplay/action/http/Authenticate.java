package com.example.screenplay.action.http;

import com.example.screenplay.action.http.client.Post;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.junit.Assert;

public class Authenticate implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    Assert.fail(
        "this process doesnt work anymore. it may still serve as example how to build steps using a custom webclient");
    String url = "https://www.rolegate.com/api/v2/api-token-auth";
    String content =
        "{\n" + "\t\"username\": \"test2\",\n" + "\t\"password\": \"asdasdasd\"\n" + "}";

    actor.attemptsTo(Post.to(url).withBody(content));
  }
}
