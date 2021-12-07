package com.example.screenplay.question.rolegate;

import com.example.screenplay.actor.Memory;
import com.example.screenplay.actor.ReceiveEmails;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import com.mailosaur.MailosaurClient;
import com.mailosaur.models.Message;
import com.mailosaur.models.SearchCriteria;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;

public class RegistrationConfirmationMessage extends QuestionWithDefaultSubject<Message> {

  @SneakyThrows
  @Override
  public Message answeredBy(Actor actor) {
    // actor.attemptsTo(FindMessage.by(emailAddress));
    SearchCriteria criteria = new SearchCriteria();
    String emailAddress = actor.recall(Memory.EMAIL_ADDRESS);
    criteria.withSentTo(emailAddress);

    MailosaurClient client = ReceiveEmails.as(actor);
    return client.messages().get("sw810bvq", criteria);
  }
}
