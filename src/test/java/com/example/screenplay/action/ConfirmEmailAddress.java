package com.example.screenplay.action;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

import com.example.screenplay.question.RegistrationConfirmationMessage;
import com.example.screenplay.question.TheyReceiveRegistrationConfirmationEmail;
import com.mailosaur.models.Message;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Open;

public class ConfirmEmailAddress implements Performable {

  @SneakyThrows
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.should(eventually(seeThat(new TheyReceiveRegistrationConfirmationEmail())));
    Message message = actor.asksFor(new RegistrationConfirmationMessage());
    String confirmationUrl = message.text().links().get(0).href();
    actor.attemptsTo(Open.url(confirmationUrl));
  }
}
