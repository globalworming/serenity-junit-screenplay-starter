package com.example.e2e.neuralnet.preparation;

import com.example.e2e.NeuralNetBase;
import com.example.screenplay.action.preparation.CreateSqliteDatabaseFromTheSurveyData;
import com.example.screenplay.action.preparation.DownloadColorSurveyData;
import com.example.screenplay.action.preparation.ExtractDataToCorrectResourcesPath;
import com.example.screenplay.error.PreconditionFailed;
import com.example.screenplay.question.preparation.DatabaseConnectionCanBeEstablished;
import com.example.screenplay.question.preparation.XkcdDataIsPresent;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@Narrative(text = "in order to do some training, we need some data, right?")
@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GettingTheTestDataIT extends NeuralNetBase {

  Actor you = new Actor("you");

  @Test
  public void t1_whereWeDownloadAndExtractTheDump() {
    you.attemptsTo(new DownloadColorSurveyData());
    you.attemptsTo(new ExtractDataToCorrectResourcesPath());
    you.should(
        eventually(seeThat(new XkcdDataIsPresent()))
            .orComplainWith(
                PreconditionFailed.class,
                "not worth automating, please DIY according to the report"));
  }

  @Test
  public void t2_whereWeImportTheDataIntoAnSqliteDatabase() {
    you.attemptsTo(new CreateSqliteDatabaseFromTheSurveyData());
    you.should(eventually(seeThat(new DatabaseConnectionCanBeEstablished())));
  }
}
