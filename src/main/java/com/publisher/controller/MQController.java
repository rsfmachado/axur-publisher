package com.publisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.publisher.PublisherApplication;
import com.publisher.amqp.MQPublisher;
import com.publisher.model.ClientURL;
import com.publisher.model.RegexPattern;

@Controller
@RequestMapping("/publisher")
public class MQController {
	
	private Logger logger = LoggerFactory.getLogger(MQController.class);
	
	@Autowired
	private MQPublisher publisher;
	
	@GetMapping("/welcome")
	public String welcome() {
		logger.debug("{} welcome(model).", PublisherApplication.APP_LOG);
		
		return "welcome";
	}
	
	@GetMapping("/insertion-form")
	public String getInsertionForm(Model model) {
		logger.debug("{} getInsertionForm(model).", PublisherApplication.APP_LOG);
		
		model.addAttribute("regexPattern", new RegexPattern());
		model.addAttribute("resultMessage", "");
		return "insertion-form";
	}
	
	@PostMapping("/insertion")
	public String publishInsertionMessage(Model model, @ModelAttribute("regexPattern") RegexPattern regexPattern) {
		logger.debug("{} publishInsertionMessage().", PublisherApplication.APP_LOG);
		
		String resultMessage = publisher.publishInsertionMessage(regexPattern);
		
		model.addAttribute("regexPattern", new RegexPattern());
		model.addAttribute("resultMessage", resultMessage);
		
		return "insertion-form";
	}
	
	@GetMapping("/validation-form")
	public String getValidationForm(Model model) {
		logger.debug("{} getValidationForm(model).", PublisherApplication.APP_LOG);
		
		model.addAttribute("clientURL", new ClientURL());
		model.addAttribute("resultMessage", "");
		return "validation-form";
	}
	
	@PostMapping("/validation")
	public String publishValidationMessage(Model model, @ModelAttribute("clientURL") ClientURL clientURL) {
		logger.debug("{} publishValidationMessage().", PublisherApplication.APP_LOG);
		
		String resultMessage = publisher.publishValidationMessage(clientURL);
		
		model.addAttribute("clientURL", new ClientURL());
		model.addAttribute("resultMessage", resultMessage);
		
		return "validation-form";
	}
}
