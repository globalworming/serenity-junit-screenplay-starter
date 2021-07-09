package com.example.screenplay.action.http;

import com.example.screenplay.action.http.client.Post;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class CreateGame implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Post.to("https://www.rolegate.com/api/v2/games").withBody("{\"expected_pace\":0,\"hidden\":false,\"allow_spectators\":true,\"allow_public_chat\":true,\"dice_sets\":[1],\"name\":\"SERENITY\",\"rules\":\"\",\"description\":\"\",\"max_players\":4,\"password\":null,\"cover\":null,\"sheet\":288209,\"tags\":[],\"public_template\":13786}"));
  }
}
