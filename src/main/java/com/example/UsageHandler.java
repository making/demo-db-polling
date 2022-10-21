package com.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class UsageHandler implements MessageHandler {
	private final Logger log = LoggerFactory.getLogger(IntegrationConfig.class);

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		@SuppressWarnings("unchecked")
		List<Usage> usages = (List<Usage>) message.getPayload();
		usages.forEach(usage -> log.info("process message: {}", usage));
	}
}
