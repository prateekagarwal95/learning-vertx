package com.prateek.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class HelloVerticle extends AbstractVerticle {


    @Override
    public void start() {
        vertx.eventBus().consumer("hello.vertx.addr", msg -> {
            msg.reply("Hello Vert.x World!! ");
        });

        vertx.eventBus().consumer("hello.names.addr", msg -> {
            String name = (String) msg.body();
            msg.reply(String.format("Hello %s !", name));
        });
    }
}
