package com.publisher.config;

import org.springframework.stereotype.Component;

@Component
public class GlobalProperties {
	
	public static String INSERTION_QUEUE = "insertion.queue";
	public static String VALIDATION_QUEUE = "validation.queue";
	
	public static String REQUEST_EXCHANGE = "request.exchange";
	
	public static String INSERTION_ROUTING_KEY = "insertion.routing.key";
	public static String VALIDATION_ROUTING_KEY = "validation.routing.key";
	
}
