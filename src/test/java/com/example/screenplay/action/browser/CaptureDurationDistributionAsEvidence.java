package com.example.screenplay.action.browser;

import lombok.SneakyThrows;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.SearchableTarget;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.OutputType;

import java.io.File;

public class CaptureDurationDistributionAsEvidence implements Performable {
  @SneakyThrows
  @Override
  public <T extends Actor> void performAs(T actor) {
    SearchableTarget target =
        Target.the("duration distribution")
            .locatedBy(
                "#root > div > div.MuiBox-root.jss6 > div.MuiBox-root.jss20 > div > div.MuiBox-root.jss77");
    File screenshot = target.resolveFor(actor).getScreenshotAs(OutputType.FILE);
    Serenity.recordReportData()
        .asEvidence()
        .withTitle(target.getName())
        .downloadable()
        .fromFile(screenshot.toPath());
  }
}
