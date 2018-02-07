package com.javanbeyond;

import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelJavaDSL extends RouteBuilder {
	private static CamelContext context;

	CamelJavaDSL() throws Exception {
		context = new DefaultCamelContext();
		// This component is for simply reading and writing to Queues
		context.addComponent("activemqSimple", getActiveMQSimpleComponent());
		// It used connection pooling to listen to Topic
		context.addComponent("activemqPool", getActiveMQPoolComponent());
		context.addComponent("jms", getJmsComponent());
		context.addRoutes(this);
		context.start();
	}

	@Override
	public void configure() throws Exception {
		// Take messages from firstQueue and send it to secondQueue
		// For topic we need to use the word "topic" after ActiveMQ
		// scheme
		from("activemqSimple:queue:firstQueue").autoStartup(false).routeId("getSendMessageQueue")
		 .to("activemqSimple:queue:secondQueue");
		// Read messages from topic with 3 consumers.
		// concurrentConsumers attribute can also be used to define
		// number of consumers

		from("activemqPool:topic:firstTopic?concurrentConsumers=3").autoStartup(false).routeId("usePoolingInActiveMQ")
				.to(ExchangePattern.InOnly, "activemq:topic:testTopicOutput")
		 		.to("stream:out");

		// if you don't mention queue or topic after ActiveMQ scheme,
		// the default value taken is queue.
		// Setting the listener class
		from("jms:firstQueue").autoStartup(false).routeId("usingMessageListenerWithActiveMQ")
		 .bean(new ActiveMQMessageListener());
	}

	public ActiveMQConnectionFactory getConnectionFactory() {
		return new ActiveMQConnectionFactory("tcp://localhost:61616");
	}

	public CamelContext getCamelContext() {
		return context;
	}

	public JmsComponent getJmsComponent() {
		// Create JmsComponent with connectionFactory
		return JmsComponent.jmsComponentAutoAcknowledge(getConnectionFactory());
	}

	// This component will be used for simple activemq connection
	public ActiveMQComponent getActiveMQSimpleComponent() {
		return ActiveMQComponent.activeMQComponent("tcp://localhost:61616");
	}

	// This component will be used for connection pooling
	public ActiveMQComponent getActiveMQPoolComponent() {
		ActiveMQComponent activeMQComponent = new ActiveMQComponent();
		activeMQComponent.setConfiguration(getJmsConfiguration());
		return activeMQComponent;
	}

	public JmsConfiguration getJmsConfiguration() {
		JmsConfiguration jmsConfiguration = new JmsConfiguration();
		// Once all the messages are sent or received, the client send
		// acknowledgement to ActiveMQ
		jmsConfiguration.setAcknowledgementMode(Session.AUTO_ACKNOWLEDGE);
		jmsConfiguration.setTransacted(false);
		// It will start at 3 parallel consumers
		jmsConfiguration.setConcurrentConsumers(3);
		jmsConfiguration.setConnectionFactory(getPooledConnectionFactory());
		return jmsConfiguration;
	}

	public PooledConnectionFactory getPooledConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		// A maximum of 10 connections can be opened on high volume of messages
		pooledConnectionFactory.setMaxConnections(10);
		pooledConnectionFactory.setConnectionFactory(getConnectionFactory());
		return pooledConnectionFactory;
	}

}
