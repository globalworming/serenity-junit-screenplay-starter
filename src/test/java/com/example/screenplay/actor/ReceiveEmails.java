package com.example.screenplay.actor;

import com.mailosaur.MailosaurClient;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;

public class ReceiveEmails implements Ability {

  private final MailosaurClient mailClient;

  public ReceiveEmails(MailosaurClient mailClient) {
    this.mailClient = mailClient;
  }

  public static ReceiveEmails with(MailosaurClient mailClient) {
    return new ReceiveEmails(mailClient);
  }

  public static MailosaurClient as(Actor actor) {
    if (actor.abilityTo(ReceiveEmails.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return actor.abilityTo(ReceiveEmails.class).mailClient;
  }
}
