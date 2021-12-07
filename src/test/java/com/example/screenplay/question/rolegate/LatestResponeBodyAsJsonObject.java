package com.example.screenplay.question.rolegate;

import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.serenitybdd.screenplay.Actor;

public class LatestResponeBodyAsJsonObject extends QuestionWithDefaultSubject<JsonObject> {
  @Override
  public JsonObject answeredBy(Actor actor) {
    return new Gson()
        .fromJson(actor.recall(Memory.LATEST_RESPONSE_BODY).toString(), JsonObject.class);
  }
}
