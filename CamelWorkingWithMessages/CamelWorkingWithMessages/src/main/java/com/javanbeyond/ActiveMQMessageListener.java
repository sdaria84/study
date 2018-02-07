package com.javanbeyond;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

class ActiveMQMessageListener implements MessageListener {
	public void onMessage(Message msg) {
		if (msg instanceof TextMessage) {
			TextMessage body = (TextMessage) msg;
			try {
				System.out.println("Message received : " + body.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Not supported. " + msg.getClass());
		}
	}
}
