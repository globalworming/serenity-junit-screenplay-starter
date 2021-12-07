package com.example.screenplay.question.rolegate;

import com.example.screenplay.actor.Memory;
import com.example.screenplay.actor.ReceiveEmails;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import com.mailosaur.MailosaurClient;
import com.mailosaur.models.Message;
import com.mailosaur.models.SearchCriteria;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;

public class TheyReceiveRegistrationConfirmationEmail extends QuestionWithDefaultSubject<Boolean> {

  @SneakyThrows
  @Override
  public Boolean answeredBy(Actor actor) {
    SearchCriteria criteria = new SearchCriteria();
    String emailAddress = actor.recall(Memory.EMAIL_ADDRESS);
    criteria.withSentTo(emailAddress);

    MailosaurClient client = ReceiveEmails.as(actor);
    Message message = client.messages().get("sw810bvq", criteria);
    return message != null;
  }
}
