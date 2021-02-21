package com.example.e2e.game.authentication.accessRoles;

import com.example.model.Role;
import com.example.screenplay.ability.GetAuthorized;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.TheirRoles;
import com.example.service.AuthenticateService;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.emptyCollectionOf;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

@RunWith(SerenityRunner.class)
public class AuthenticationTest {

  private final Actor user = new Actor("User");

  @Before
  public void setUp() {
    user.can(GetAuthorized.through(AuthenticateService.getInstance()));
  }

  @Test
  public void whenAuthorizingAsAdmin() {
    user.remember(Memory.ACCESS_TOKEN, "adminAccessToken");
    TheirRoles theirRoles = new TheirRoles();
    user.should(seeThat(theirRoles, hasSize(1)), seeThat(theirRoles, hasItems(Role.ADMIN)));
  }

  @Test
  public void whenHavingNoAuthorization() {
    user.should(seeThat(new TheirRoles(), emptyCollectionOf(Role.class)));
  }

  @Test
  public void whenAuthorizingAsPlayer() {
    user.remember(Memory.ACCESS_TOKEN, "playerAccessToken");
    TheirRoles theirRoles = new TheirRoles();
    user.should(seeThat(theirRoles, hasSize(1)), seeThat(theirRoles, hasItems(Role.PLAYER)));
  }
}
