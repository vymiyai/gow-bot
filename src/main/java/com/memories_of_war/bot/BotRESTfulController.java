package com.memories_of_war.bot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotRESTfulController {
	
	@RequestMapping("/")
	public String index() {
		return "VV-bot is probably alive now.";
	}
}