package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
public class RestToQueue {

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendToQueue(String message) {

		jmsTemplate.convertAndSend("message", message);
	}
}
