package io.vertx.sample;

import io.vertx.core.AbstractVerticle;

/**
 * A verticle extends the AbstractVerticle class.
 */
public class MyFirstVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        // We create a HTTP server object
        vertx.createHttpServer()
                // The requestHandler is called for each incoming
                // HTTP request, we print the name of the thread
                .requestHandler(req -> {
                    req.response().end("Hello from "
                            + Thread.currentThread().getName());
                })
                .listen(8080); // start the server on port 8080
    }
}