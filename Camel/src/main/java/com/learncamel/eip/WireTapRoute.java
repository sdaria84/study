package com.learncamel.eip;

import org.apache.camel.builder.RouteBuilder;

public class WireTapRoute extends RouteBuilder {

    public void configure() throws Exception {
        from("file:input?noop=true")
                .wireTap("file:debug")
                .to("file:all");
    }
}