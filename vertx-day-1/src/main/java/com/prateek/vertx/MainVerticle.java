package com.prateek.vertx;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // HTTP Hello
        vertx.createHttpServer().requestHandler(req -> {
            req.response().end("Hello Vertx Day 1 !!");
        }).listen(8080);
    }

}
