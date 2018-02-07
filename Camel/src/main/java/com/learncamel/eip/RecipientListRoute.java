package com.learncamel.eip;

import org.apache.camel.builder.RouteBuilder;

public class RecipientListRoute extends RouteBuilder {

    public void configure() throws Exception {

        from("file:xmlinput?noop=true")
                .setHeader("type", xpath("/employee/@type"))
                .process(new RecipientEIPProcessor())
                .recipientList(header("type"));

    }
}