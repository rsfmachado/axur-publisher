package com.publisher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class MQConfiguration implements RabbitListenerConfigurer {
		
	@Bean
	Queue insertionQueue() {
		return new Queue(GlobalProperties.INSERTION_QUEUE);
	}
	
	@Bean
	Queue validationQueue() {
		return new Queue(GlobalProperties.VALIDATION_QUEUE);
	}
	
	@Bean
	DirectExchange requestExchange() {
		return new DirectExchange(GlobalProperties.REQUEST_EXCHANGE);
	}
	
	@Bean
	Binding insertionBinding(Queue insertionQueue, DirectExchange requestExchange) {
		return BindingBuilder.bind(insertionQueue).to(requestExchange).with(GlobalProperties.INSERTION_ROUTING_KEY);
	}
	
	@Bean
	Binding validationBinding(Queue validationQueue, DirectExchange requestExchange) {
		return BindingBuilder.bind(validationQueue).to(requestExchange).with(GlobalProperties.VALIDATION_ROUTING_KEY);
	}
		
    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
