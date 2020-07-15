package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.basuha.breed_bot.message.User;
import com.basuha.breed_bot.repository.MessageRepo;
import com.basuha.breed_bot.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/api/customer")
public class RestWebController {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private BreedService breedService;

	@GetMapping(value = "/all")
	public Response getAllMessages() {
		return new Response("Done", messageRepo.findAll());
	}

	@PostMapping(value = "/save")
	public Response postMessage(@RequestBody Message message) {
		messageRepo.save(message);
		// Create Response Object
		return new Response("Done", message); //TODO:
	}

	@GetMapping(value = "/response")
	public Response sendMessageToUser() {
		Message response = new Message();
		String url = "https://dog.ceo/api/breed/ovcharka/images/random";
		response.setText(breedService.getPlainJSON(url));
//		response.setAuthor(user);
		messageRepo.save(response);

		// Create Response Object
		return breedService.parseResponse(response); //TODO:
	}
}