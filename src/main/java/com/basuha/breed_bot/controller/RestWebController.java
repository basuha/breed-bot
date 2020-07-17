package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.service.Response;
import com.basuha.breed_bot.repository.MessageRepo;
import com.basuha.breed_bot.repository.UserRepo;
import com.basuha.breed_bot.service.BotService;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/breed-bot")
public class RestWebController {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BotService botService;

	private final Map<Long,Queue<Message>> requestQueue = new HashMap<>();

	@GetMapping
	public List<Message> getAllMessages(@RequestParam Long chatId) { //Initialize chat messages from DB
		Message message = Message.builder()
				.isBotMessage(true)
				.userId(chatId)
				.timestamp(System.currentTimeMillis())
				.build();
		String username = userRepo.findById(chatId).get().getUsername();

		if (!messageRepo.existsByUserId(chatId))
			message.setText(String.format(botService.getBotWelcomeMessage(), username)); //if user is absent, welcome message building
		else
			message.setText(String.format(botService.getRandomBotGreetingMessage(), username)); //if user is present, greeting message building

		messageRepo.save(message); //save start message into repository
		return messageRepo.getByUserIdOrderByTimestamp(chatId);
	}

	@PostMapping(value = "/save")
	public Response postMessage(@RequestBody String request) { //TODO: add message validation
		Message message = botService.jsonToMessage(request); //parsing json
		message.setTimestamp(System.currentTimeMillis());    //set current time
		Long chatId = message.getUserId();					 //using user id for chat identification
															 //every chat has it own message queue
		if (!requestQueue.containsKey(chatId)){				 //is chat`s queue present checking
			Queue<Message> queue = new LinkedList<>();
			requestQueue.put(chatId, queue);
		}

		requestQueue.get(chatId).offer(message);			 //offer message to chats queue
		return new Response("Done", request); //TODO:
	}

	@GetMapping(value = "/response")
	@ResponseBody
	public List<Message> sendMessageToUser(@RequestParam Long chatId) { //bot response building
		List<Message> requests = new ArrayList<>();
		List<Message> responses = new ArrayList<>();
		Message message = null;

		do
			if (requestQueue.containsKey(chatId))
				message = requestQueue.get(chatId).poll();
		while (message == null);

		if (message.getText() != null) {
			List<String> parsedKeyWords = botService.parseUserMessage(message.getText());
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
						response.setData(botService.getBreedListJson());
						response.setType("list");
					}
					case "random" -> {
						response.setData(botService.getRandomDogImage());
						response.setText(botService.getRandomBotText());
						response.setType("image");
					}
					case "hello","hi" ->
						response.setText(String.format(
								botService.getRandomBotGreetingMessage(),
								userRepo.findById(chatId).get().getUsername()));

					case "help" ->
						response.setText(botService.getBotHelpMessage());

					default -> {
						response.setData(botService.getRandomDogImageByBreed(request.getKeyword()));
						response.setText(botService.getRandomBotText()
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