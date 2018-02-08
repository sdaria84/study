package com.learncamel.transform;

import org.apache.camel.builder.RouteBuilder;

public class CamelModifyTransformRoute extends RouteBuilder {

    public void configure() throws Exception {

        from("process:transformInput")
                .transform(body().regexReplaceAll(",","*"))
                .to("mock:output");
    }
}