package com.learncamel.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.ConnectionFactory;

import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent;

public class JmsReadTopic extends RouteBuilder {

    public void configure() throws Exception {


       from("activemq:topic:testTopicInput")
       // from("activemq:queue:testQueue")
                .to("log:?level=INFO&showBody=true")
                .to("direct:readQueue")
                .to(ExchangePattern.InOnly, "activemq:topic:testTopicOutput");
                //.to("direct:readQueue");

    }
}