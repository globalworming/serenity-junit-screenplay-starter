package com.example.screenplay.action.preparation;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static net.serenitybdd.core.Serenity.reportThat;

public class CreateSqliteDatabaseFromTheSurveyData implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    reportThat(
        "run 'cat $project_dir/src/main/resources/xkcd/mainsurvey_sqldump.txt | sqlite3 $project_dir/src/main/resources/xkcd/xkcd.db'",
        () -> {});
  }
}
