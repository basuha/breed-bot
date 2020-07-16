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

	@GetMapping
	public List<Message> getAllMessages(@RequestParam Long chatId) {
		return messageRepo.getByUserId(chatId);
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
		return new Response("Done", request); //TODO:
	}

	@GetMapping(value = "/response")
	@ResponseBody
	public List<Message> sendMessageToUser(@RequestParam Long chatId) {
		List<Message> requests = new ArrayList<>();
		List<Message> responses = new ArrayList<>();
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
		messageRepo.save(message);

		for (var request : requests) {
			Message response = new Message();
			response.setStatus("success");
			response.setUserId(chatId);
			response.setIsBotMessage(true);

			switch (request.getText()) {
				case "list" -> {
					response.setText(breedService.getRandomBotText());
					response.setType("list");
				}
				case "random" -> {
					response.setData(breedService.getRandomDogImage());
					response.setText(breedService.getRandomBotText());
					response.setType("image");
				}
				default -> response = null;
			}
			responses.add(response);
			if (response != null) {
				messageRepo.save(response);
			}
		}
		System.out.println(responses.size());
		return responses;
	}
}