package com.learncamel;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by d.v.sergeeva on 26.01.2018.
 */
public class CopyFilesRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:data/input?noop=true")
                .to("log:?level=INFO&showBody=true&showHeaders=true")
                .log("Received Message is ${body} and Headers are ${headers}")
                .to("file:data/output");
    }
}
