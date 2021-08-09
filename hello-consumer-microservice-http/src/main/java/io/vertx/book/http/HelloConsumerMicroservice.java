package io.vertx.book.http;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.*;
import io.vertx.rxjava.ext.web.client.*;
import io.vertx.rxjava.ext.web.codec.BodyCodec;
import rx.Single;

public class HelloConsumerMicroservice extends AbstractVerticle {

    private WebClient client;
    @Override
    public void start() {

        client = WebClient.create(vertx);
        Router router = Router.router(vertx);
        router.get("/").handler(this::invokeMyFirstMicroservice);

        vertx.createHttpServer().requestHandler(router::accept)
                .listen(8081);
    }

    private void invokeMyFirstMicroservice(RoutingContext rc) {
        HttpRequest<JsonObject> request1 = client
                .get(8080, "localhost", "/Luke")
                .as(BodyCodec.jsonObject());
        HttpRequest<JsonObject> request2 = client
                .get(8080, "localhost", "/Leia")
                .as(BodyCodec.jsonObject());
        Single<JsonObject> s1 = request1.rxSend()
                .map(HttpResponse::body);
        Single<JsonObject> s2 = request2.rxSend()
                .map(HttpResponse::body);
        Single
                .zip(s1, s2, (luke, leia) -> {
                    // We have the results of both requests in Luke and Leia
                    System.out.println(luke);
                    return new JsonObject()
                            .put("Luke", luke.getString("message"))
                            .put("Leia", leia.getString("message"));
                })
                .subscribe(
                        result -> rc.response().end(result.encodePrettily()),
                        error -> {
                            error.printStackTrace();
                            rc.response()
                                    .setStatusCode(500).end(error.getMessage());
                        }
                );
    }
}
