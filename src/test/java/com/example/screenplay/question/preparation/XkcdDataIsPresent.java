package com.example.screenplay.question.preparation;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.io.FileMatchers.aReadableFile;

public class XkcdDataIsPresent extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    actor.should(
        seeThat(
            "required file './src/main/resources/xkcd/mainsurvey_sqldump.txt'",
            (a) -> {
              URL url = ClassLoader.getSystemResource("./xkcd/mainsurvey_sqldump.txt");
              if (url == null) {
                return null;
              }
              try {
                return new File(url.toURI());
              } catch (URISyntaxException e) {
                throw new RuntimeException(e);
              }
            },
            aReadableFile()));
    return true;
  }
}
