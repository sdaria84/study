package com.learncamel.eip;

import org.apache.camel.builder.RouteBuilder;

public class MultiCastRoute extends RouteBuilder {

    public void configure() throws Exception {

       /* from("file:input?noop=true").multicast()
                .to("file:output1", "file:output2");*/

        //parallel Processing

        /*from("file:input?noop=true").multicast().parallelProcessing()
                .to("file:output1", "file:output2");*/

        //Exception Handling
        from("file:input?noop=true").multicast().stopOnException()
                .parallelProcessing()
                .to("file:output1", "file:output2");

    }
}