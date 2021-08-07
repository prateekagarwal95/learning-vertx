package com.prateek.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);
        // Here handler takes RoutingContext not just request
        router.get("/api/v1/hello").handler(this::helloVertx);
        router.get("/api/v1/hello/:name").handler(this::helloName);
        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

    void helloVertx(RoutingContext routingContext) {
        routingContext.request().response().end("Hello Vert.x World");
    }

    void helloName(RoutingContext routingContext) {
        String name = routingContext.pathParam("name");
        routingContext.request().response().end(String.format("Hello %s !!", name));
    }

}
