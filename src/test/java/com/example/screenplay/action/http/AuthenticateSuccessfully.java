package com.example.screenplay.action.http;

import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.LatestResponeBodyAsJsonObject;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.hamcrest.text.IsBlankString;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthenticateSuccessfully implements Performable {

  @SneakyThrows
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(new Authenticate());

    String accessToken = new LatestResponeBodyAsJsonObject().answeredBy(actor).get("token").getAsString();
    assertThat(accessToken, not(IsBlankString.blankOrNullString()));
    actor.remember(Memory.ACCESS_TOKEN, accessToken);
  }
}
