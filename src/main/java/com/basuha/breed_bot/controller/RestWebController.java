package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.basuha.breed_bot.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class RestWebController {

	@Autowired
	private MessageRepo messageRepo;

	@GetMapping(value = "/all")
	public Response getAllMessages() {
		return new Response("Done", messageRepo.findAll());
	}

	@PostMapping(value = "/save")
	public Response postMessage(@RequestBody Message message) {
		messageRepo.save(message);
		Message response = new Message();
		response.setText("asdasddasdasasdasd");
		// Create Response Object
		return new Response("Done", message); //TODO:
	}
//
//	@PostMapping(value = "/save")
//	public Response sendMessageToUser(@RequestBody Message message) {
//		messageRepo.save(message);
//
//		// Create Response Object
//		return new Response("Done", message); //TODO:
//	}
}