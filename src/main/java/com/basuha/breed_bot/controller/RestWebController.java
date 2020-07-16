package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.basuha.breed_bot.repository.MessageRepo;
import com.basuha.breed_bot.repository.UserRepo;
import com.basuha.breed_bot.service.BreedService;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/breed-bot")
public class RestWebController {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BreedService breedService;

	private final Map<Long,Queue<Message>> requestQueue = new HashMap<>();

	@Value("${bot-welcome-message}")
	private String botWelcomeMessage;

	@GetMapping
	public List<Message> getAllMessages(@RequestParam Long chatId) {
		messageRepo.save(Message.builder() //Welcome message building
				.isBotMessage(true)
				.userId(chatId)
				.timestamp(System.currentTimeMillis())
				.text(String.format(botWelcomeMessage, userRepo.findById(chatId).get().getUsername()))
				.build());
		return messageRepo.getByUserIdOrderByTimestamp(chatId);
	}

	@PostMapping(value = "/save")
	public Response postMessage(@RequestBody String request) { //TODO: add message validation
		Message message = breedService.jsonToMessage(request);
		message.setTimestamp(System.currentTimeMillis());
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
					requests.add(Message.builder()
							.keyword(s)
							.build());
					System.out.println(s);
				}
			}
		}

		if (requests.isEmpty())
			requests.add(message);
		messageRepo.save(message);

		for (var request : requests) {
			Message response = Message.builder()
					.status("success")
					.userId(chatId)
					.isBotMessage(true)
					.timestamp(System.currentTimeMillis())
					.build();

			if (request.getKeyword() != null) {
				switch (request.getKeyword()) {
					case "list" -> {
						response.setText("Here`s a breed list. You can choose multiple");
						response.setData(breedService.getBreedListJson());
						response.setType("list");
					}
					case "random" -> {
						response.setData(breedService.getRandomDogImage());
						response.setText(breedService.getRandomBotText());
						response.setType("image");
					}
//				case "help" -> { //TODO: help command
//
//				}
					default -> {
						response.setData(breedService.getRandomDogImageByBreed(request.getKeyword()));
						response.setText(breedService.getRandomBotText()
								+ " Picture of <b>"
								+ WordUtils.capitalizeFully(request.getKeyword())
								+ "</b>");
						response.setType("image");
					}
				}
			} else {
				response = null;
			}

			responses.add(response);
			if (response != null) {
				messageRepo.save(response);
			}
		}
		return responses;
	}
}