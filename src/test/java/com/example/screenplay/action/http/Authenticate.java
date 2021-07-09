package com.example.screenplay.action.http;

import com.example.screenplay.action.http.client.Post;
import com.example.screenplay.actor.Memory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class Authenticate implements Performable {

  @SneakyThrows
  @Override
  public <T extends Actor> void performAs(T actor) {
    String url = "https://www.rolegate.com/api/v2/api-token-auth";
    String content = "{\n" +
        "\t\"username\": \"test2\",\n" +
        "\t\"password\": \"asdasdasd\"\n" +
        "}";

    actor.attemptsTo(Post.to(url).withBody(content));

    String responseBody = actor.recall(Memory.LATEST_RESPONSE_BODY);
    String accessToken = new Gson().fromJson(responseBody, JsonObject.class).get("token").getAsString();
    actor.remember(Memory.ACCESS_TOKEN, accessToken);
  }
}
