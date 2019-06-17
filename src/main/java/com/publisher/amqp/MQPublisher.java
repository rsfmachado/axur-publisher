package com.publisher.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publisher.PublisherApplication;
import com.publisher.config.GlobalProperties;
import com.publisher.model.RegexPattern;
import com.publisher.model.ClientURL;
import com.publisher.util.PublisherUtil;

@Component
public class MQPublisher {
	
	private Logger logger = LoggerFactory.getLogger(MQPublisher.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
 	public String publishInsertionMessage(RegexPattern regexPattern) {
		logger.debug("{} publishInsertionMessage().", PublisherApplication.APP_LOG);
		
	    try {
	         String messageJson = objectMapper.writeValueAsString(regexPattern);
	         Message message = MessageBuilder
	                                .withBody(messageJson.getBytes())
	                                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
	                                .build();
	         this.rabbitTemplate.convertAndSend(GlobalProperties.REQUEST_EXCHANGE, GlobalProperties.INSERTION_ROUTING_KEY, message);
	     } catch (JsonProcessingException e) {
	         e.printStackTrace();
	     }
		
		return "Insertion Message published in RabbitMQ Exchange ["+GlobalProperties.REQUEST_EXCHANGE+"] with RoutingKey ["+GlobalProperties.INSERTION_ROUTING_KEY+"] at " + PublisherUtil.getCurrentTimestamp();
	}
	
	public String publishValidationMessage(ClientURL clientURL) {
		logger.debug("{} publishValidationMessage().", PublisherApplication.APP_LOG);
		
	    try {
	         String messageJson = objectMapper.writeValueAsString(clientURL);
	         Message message = MessageBuilder
	                                .withBody(messageJson.getBytes())
	                                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
	                                .build();
	         this.rabbitTemplate.convertAndSend(GlobalProperties.REQUEST_EXCHANGE, GlobalProperties.VALIDATION_ROUTING_KEY, message);
	     } catch (JsonProcessingException e) {
	         e.printStackTrace();
	     }
	    
		return "Validation Message published in RabbitMQ Exchange ["+GlobalProperties.REQUEST_EXCHANGE+"] with RoutingKey ["+GlobalProperties.VALIDATION_ROUTING_KEY+"] at " + PublisherUtil.getCurrentTimestamp();
	}
	
}
