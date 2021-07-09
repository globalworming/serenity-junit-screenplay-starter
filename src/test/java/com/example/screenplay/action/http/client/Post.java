package com.example.screenplay.action.http.client;

import com.example.screenplay.ability.PerformHttpsRequests;
import com.example.screenplay.actor.Memory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static net.serenitybdd.screenplay.Tasks.instrumented;

@RequiredArgsConstructor
public class Post implements Performable {
  private static Log log = LogFactory.getLog(Post.class);


  private final String url;
  private final String content;

  public static Post to(String url) {
    return new Post(url, null);
  }

  @SneakyThrows
  @Override
  @Step("{0} performs post on <#url> with body <#content>")
  public <T extends Actor> void performAs(T actor) {
    val client = PerformHttpsRequests.as(actor);
    val body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), content);
    String csrfToken = actor.recall(Memory.CSRF_TOKEN);
    String accessToken = actor.recall(Memory.ACCESS_TOKEN);

    Request request = buildRequest(csrfToken, body, accessToken);
    Response response = client.newCall(request).execute();
    actor.remember(Memory.LATEST_RESPONSE, response);
    String responseBody = response.body().string();
    actor.remember(Memory.LATEST_RESPONSE_BODY, responseBody);

    if (log.isDebugEnabled()) {
      log.debug(responseBody);
    }

  }

  private Request buildRequest(String csrfToken, RequestBody body, String accessToken) {
    val builder = new Request.Builder()
        .url(url)
        .header("cookie", "csrftoken=" + csrfToken)
        .header("referer", "https://www.rolegate.com/login")
        .header("origin", "https://www.rolegate.com")
        .post(body);


    if (!StringUtils.isBlank(accessToken)) {
      builder.header("authorization", "Token " + accessToken);
    }


    return builder.build();
  }

  public Post withBody(String content) {
    return instrumented(Post.class, url, content);
  }
}
