package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.basuha.breed_bot.message.User;
import com.basuha.breed_bot.repository.MessageRepo;
import com.basuha.breed_bot.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("/api/customer")
public class RestWebController {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private BreedService breedService;

	private final Queue<Message> requestQueue = new ConcurrentLinkedQueue<>();

	@GetMapping(value = "/all")
	public Response getAllMessages() {
		return new Response("Done", messageRepo.findAll());
	}

	@PostMapping(value = "/save")
	public Response postMessage(@RequestBody String request) {
		Message message = breedService.jsonToMessage(request);
		requestQueue.offer(message);
		messageRepo.save(message);
		return new Response("Done", request); //TODO:
	}

	@GetMapping(value = "/response")
	public Response sendMessageToUser() {
		Message request;
		for(;;) {
			request = requestQueue.poll();
			if (request != null)
				break;
		}

		Message response = new Message();
		String url = "https://dog.ceo/api/breed/ovcharka/images/random";
		response.setData(breedService.getPlainJSON(url));
		response.setText("Картинка по запросу: " + request.getText());
		messageRepo.save(response);

		// Create Response Object
		return breedService.parseResponse(response); //TODO:
	}
}