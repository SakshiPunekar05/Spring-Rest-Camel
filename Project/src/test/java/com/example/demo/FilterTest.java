package com.example.demo;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FilterTest{

	@EndpointInject(value = "mock:direct:convert")
	protected MockEndpoint getMockEndpoint;
	
	@Produce(value = "direct:start")
    protected ProducerTemplate template;

	
	@Test
    public void testMock() throws Exception {
        getMockEndpoint.expectedBodiesReceived("Hello World");

        template.sendBody("direct:start", "Hello World");

        getMockEndpoint.assertIsSatisfied();
    }

    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("mock:result");
            }
        };
    }

}