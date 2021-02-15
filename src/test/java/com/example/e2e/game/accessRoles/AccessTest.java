package com.example.e2e.game.accessRoles;


import com.example.E2eBase;
import com.example.screenplay.action.CreatesGame;
import com.example.screenplay.action.JoinsAGame;
import com.example.screenplay.action.SetupGame;
import com.example.screenplay.question.PlayersPlaying;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SerenityRunner.class)
public class AccessTest extends E2eBase {

  @Test
  public void whenAdminsCreatePlayersCanJoin() {
    admin.attemptsTo(new CreatesGame());
    lizzy.attemptsTo(new JoinsAGame());
    admin.should(seeThat(new PlayersPlaying(), hasSize(1)));
  }

  @Test
  public void whenAdminRemovesPlayersOthersCanJoin() {
    admin.attemptsTo(SetupGame.forPlayers(players.getActors()));
    lizzy.should(seeThat(new PlayersPlaying(), hasSize(2)));
    //admin.attemptsTo(DeletePlayer.named(alex).from(gameName));
  }
}
