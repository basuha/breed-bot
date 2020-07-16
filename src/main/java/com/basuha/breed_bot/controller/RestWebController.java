package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.basuha.breed_bot.message.User;
import com.basuha.breed_bot.repository.MessageRepo;
import com.basuha.breed_bot.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
			Queue<Message> queue = new ConcurrentLinkedQueue<>();
			requestQueue.put(chatId, queue);
		}

		List<String> parsedKeyWords = breedService.parseUserMessage(message.getText());
		if (!parsedKeyWords.isEmpty())
			for(var s : parsedKeyWords) {
				System.out.println(s);
		}

		requestQueue.get(chatId).offer(message);
		messageRepo.save(message);
		return new Response("Done", request); //TODO:
	}

	@GetMapping(value = "/response")
	@ResponseBody
	public Response[] sendMessageToUser(@RequestParam Long chatId) {
		Message request = null;
		do {
			if (requestQueue.containsKey(chatId)){
				request = requestQueue.get(chatId).poll();
			}
		} while (request == null);

		Message response = new Message();
		String url = "https://dog.ceo/api/breed/ovcharka/images/random";
		response.setUserId(chatId);
		response.setIsBotMessage(true);
		response.setData(breedService.getPlainJSON(url));
		response.setText("Картинка по запросу: " + request.getText());
		messageRepo.save(response);

		// Create Response Object
		return new Response[] {breedService.parseResponse(response), breedService.parseResponse(response)}; //TODO:
	}
}