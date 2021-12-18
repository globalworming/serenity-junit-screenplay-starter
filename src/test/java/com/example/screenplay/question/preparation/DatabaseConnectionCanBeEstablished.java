package com.example.screenplay.question.preparation;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import org.hamcrest.CoreMatchers;
import org.sqlite.SQLiteConnection;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import static net.serenitybdd.core.Serenity.reportThat;
import static org.hamcrest.MatcherAssert.assertThat;

public class DatabaseConnectionCanBeEstablished extends QuestionWithDefaultSubject<Boolean> {

  @SneakyThrows
  @Override
  public Boolean answeredBy(Actor actor) {
    AtomicLong firstDateStamp = new AtomicLong();

    reportThat(
        "connect to xkcd.db and query the first answers datestamp",
        () -> {
          try {
            Class.forName("org.sqlite.JDBC");
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
          URL url = ClassLoader.getSystemResource("./xkcd/xkcd.db");
          try (SQLiteConnection connection =
              (SQLiteConnection) DriverManager.getConnection("jdbc:sqlite:" + url.getFile())) {
            ResultSet resultSet =
                connection.prepareStatement("SELECT datestamp FROM answers LIMIT 1").executeQuery();
            resultSet.next();
            firstDateStamp.set(resultSet.getLong(1));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
    long expected = 1267418734L;
    reportThat(
        "then check first answer datestamp is " + expected,
        () -> assertThat(firstDateStamp.get(), CoreMatchers.is(expected)));
    return true;
  }
}
