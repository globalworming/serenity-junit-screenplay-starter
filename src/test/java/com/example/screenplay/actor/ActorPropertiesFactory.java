package com.example.screenplay.actor;

import com.github.javafaker.Faker;
import java.util.Random;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class ActorPropertiesFactory {

  @NotNull
  public static String getUniqueUsername() {

    return new Faker().funnyName().name() + new Random().nextInt();
  }

  public static String randomUniqueEmailAddress() {
    return UUID.randomUUID().toString() + "@sw810bvq.mailosaur.net";
  }
}
