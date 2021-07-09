package com.example.screenplay.action.http;

import com.example.screenplay.ability.PerformHttpsRequests;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.SneakyThrows;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import okhttp3.Request;

public class CsrfToken extends QuestionWithDefaultSubject<String> {
  @SneakyThrows
  @Override
  public String answeredBy(Actor actor) {
    val response = PerformHttpsRequests.as(actor).newCall(new Request.Builder()
        .url("https://www.rolegate.com/login")
        .build()).execute();
    String cookie = response.header("set-cookie");

    return cookie.split("=")[1].split(";")[0];
  }
}
