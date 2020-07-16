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
import java.util.concurrent.ArrayBlockingQueue;
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

		if (!requestQueue.containsKey(chatId)){
			Queue<Message> queue = new LinkedList<>();
			requestQueue.put(chatId, queue);
		}

		requestQueue.get(chatId).offer(message);
		messageRepo.save(message);

		return new Response("Done", request); //TODO:
	}

	@GetMapping(value = "/response")
	@ResponseBody
	public List<Response> sendMessageToUser(@RequestParam Long chatId) {
		List<Message> requests = new ArrayList<>();
		List<Response> responses = new ArrayList<>();
		Message message = null;

		do
			if (requestQueue.containsKey(chatId))
				message = requestQueue.get(chatId).poll();
		while (message == null);

		if (message.getText() != null) {
			List<String> parsedKeyWords = breedService.parseUserMessage(message.getText());
			if (!parsedKeyWords.isEmpty()) {
				for (String s : parsedKeyWords) {
					Message responseKeyword = new Message();
					responseKeyword.setText(s);
					requests.add(responseKeyword);
					System.out.println(s);
				}
			}
		}

		if (requests.isEmpty())
			requests.add(message);

		for (var request : requests) {
			Message response = new Message();
			String url = "https://dog.ceo/api/breeds/image/random"; //TODO:
			response.setUserId(chatId);
			response.setIsBotMessage(true);
			response.setData(breedService.getPlainJSON(url));
			response.setText("Картинка по запросу: "); //+ request.getText());
			responses.add(breedService.parseResponse(response));
			messageRepo.save(request);
		}

		System.out.println(responses.size());

		return responses;
	}
}