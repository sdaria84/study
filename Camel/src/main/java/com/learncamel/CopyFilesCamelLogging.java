package com.learncamel;

/**
 * Created by d.v.sergeeva on 26.01.2018.
 */

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

//https://stackoverflow.com/questions/39976339/failed-to-load-class-org-slf4j-impl-staticloggerbinder-error

public class CopyFilesCamelLogging {

    public static void main(String[] args) {
        CamelContext context= new DefaultCamelContext();

        try{
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:data/input?noop=true")
                            .to("log:?level=INFO&showBody=true&showHeaders=true")
                            .log("Received Message is ${body} and Headers are ${headers}")
                            .to("file:data/output");


                }
            });

            context.start();
            Thread.sleep(5000);
            context.stop();

        }catch(Exception e){
            System.out.println("Inside Exception : " + e);
        }

    }
}
