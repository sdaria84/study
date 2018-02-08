package com.learncamel.eip;

//import com.learncamel.aggregator.AggregatorSimpleRouteStrategy;
import org.apache.camel.builder.RouteBuilder;

public class AggregatorSimpleRoute extends RouteBuilder{

    public void configure() throws Exception {
        from("direct:simpleAggregator")
                .log("Recieved Message is ${body} and key ${header.aggregatorId}")
                .aggregate(header("aggregatorId"), new AggregatorSimpleRouteStrategy()).completionSize(3)
                .log("Aggregated Message is ${body}")
                .to("mock:output");



    }
}