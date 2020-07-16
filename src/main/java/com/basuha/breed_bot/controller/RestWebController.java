package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.basuha.breed_bot.message.User;
import com.basuha.breed_bot.repository.MessageRepo;
import com.basuha.breed_bot.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("/api/customer")
public class RestWebController {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private BreedService breedService;

	private final Map<Long,Queue<Message>> requestQueue = new HashMap<>();

	@GetMapping()
	public Response getAllMessages(@RequestParam Long chatId) {
		return new Response("Done", messageRepo.getByUserId(chatId));
	}

	@PostMapping(value = "/save")
	public Response postMessage(@RequestBody String request) {
		Message message = breedService.jsonToMessage(request);
		Long chatId = message.getUserId();
		List<Response> responses = new ArrayList<>();

		if (!requestQueue.containsKey(chatId)){
			Queue<Message> queue = new ConcurrentLinkedQueue<>();
			requestQueue.put(chatId, queue);
		}

//		List<String> parsedKeyWords = breedService.parseUserMessage(message.getText());
//		if (!parsedKeyWords.isEmpty())
//			for(var s : parsedKeyWords) {
//				System.out.println(s);
//		}

		requestQueue.get(chatId).offer(message);
		messageRepo.save(message);
		return new Response("Done", request); //TODO:
	}

	@GetMapping(value = "/response")
	@ResponseBody
	public List<Response> sendMessageToUser(@RequestParam Long chatId) {
		List<Message> requests = new ArrayList<>();
		List<Response> responses = new ArrayList<>();

		do {
			if (requestQueue.containsKey(chatId)){
				do {
					requests.add(requestQueue.get(chatId).poll());
				} while (requestQueue.get(chatId).size() > 0);
			}
		} while (requests.isEmpty());

		System.out.println(requests.size());

		for (var request : requests) {
			Message response = new Message();

			String url = "https://dog.ceo/api/breed/ovcharka/images/random"; //TODO:
			response.setUserId(chatId);
			response.setIsBotMessage(true);
			response.setData(breedService.getPlainJSON(url));
			response.setText("Картинка по запросу: "); //+ request.getText());

			messageRepo.save(response);
			responses.add(breedService.parseResponse(response));
		}

		return responses;
	}
}