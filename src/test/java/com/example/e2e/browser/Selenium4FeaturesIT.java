package com.example.e2e.browser;

import com.example.screenplay.action.AccessTheLatestReport;
import com.example.screenplay.action.browser.CaptureDurationDistributionAsEvidence;
import com.example.screenplay.action.browser.EnsureButtonTitles;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v99.network.Network;
import org.openqa.selenium.remote.http.HttpResponse;

import java.io.IOException;
import java.util.Optional;

public class Selenium4FeaturesIT {

  private OnlineCast cast = new OnlineCast();

  @Test
  public void selenium4screenshotExample() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(new CaptureDurationDistributionAsEvidence());
  }

  @Test
  public void selenium4relativeLocatorsExample() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(new EnsureButtonTitles());
  }

  @Test
  public void selenium4devToolsLogRequestsExample() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    DevTools devTools = BrowseTheWeb.as(tester).getDevTools();
    devTools.createSessionIfThereIsNotOne();
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
    devTools.addListener(
        Network.responseReceived(),
        entry -> {
          System.out.println(
              "Response (Req id) URL : ("
                  + entry.getRequestId()
                  + ") "
                  + entry.getResponse().getUrl()
                  + " ("
                  + entry.getResponse().getStatus()
                  + ")");
        });
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(Open.url("https://example.com/"));
    // tester.attemptsTo(EnsureNoStatus500.in(responses));
  }

  @Test
  public void selenium4devToolsInterceptRequests() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    DevTools devTools = BrowseTheWeb.as(tester).getDevTools();
    devTools.createSessionIfThereIsNotOne();
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
    devTools
        .getDomains()
        .network()
        .interceptTrafficWith(
            httpHandler ->
                req -> {
                  if (req.getUri().contains("example")) {
                    Call call =
                        new OkHttpClient()
                            .newCall(
                                new Request.Builder()
                                    .url("https://placekitten.com/g/200/300")
                                    .get()
                                    .build());
                    try {
                      Response response = call.execute();
                      HttpResponse httpResponse = new HttpResponse();
                      httpResponse.setContent(() -> response.body().byteStream());
                      return httpResponse;
                    } catch (IOException e) {
                      throw new RuntimeException(e);
                    }
                  }
                  return httpHandler.execute(req);
                });
    tester.attemptsTo(Open.url("https://example.com/"));
  }
}
