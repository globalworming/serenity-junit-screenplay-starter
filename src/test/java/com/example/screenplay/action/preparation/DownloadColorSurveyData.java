package com.example.screenplay.action.preparation;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class DownloadColorSurveyData implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    Serenity.reportThat("you download http://xkcd.com/color/colorsurvey.tar.gz", () -> {});
  }
}
