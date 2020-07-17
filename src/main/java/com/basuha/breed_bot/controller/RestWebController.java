package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.service.Response;
import com.basuha.breed_bot.repository.MessageRepo;
import com.basuha.breed_bot.repository.UserRepo;
import com.basuha.breed_bot.service.BotService;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	public Response postMessage(@RequestBody Message request) {
		//parsing json
		Message message = request;

		//set current time
		message.setTimestamp(System.currentTimeMillis());

		//using user id for chat identification
		Long chatId = message.getUserId();

		//every chat has it own message queue
		if (!requestQueue.containsKey(chatId)){

			//is chat`s queue present checking
			Queue<Message> queue = new LinkedList<>();
			requestQueue.put(chatId, queue);
		}

		//offer message to chats queue
		requestQueue.get(chatId).offer(message);
		return new Response("Done", request); //TODO:
	}

	@GetMapping(value = "/response")
	@ResponseBody
	public List<Message> sendMessageToUser(@RequestParam Long chatId) { //bot response building
		Message message = null;
		do
			if (requestQueue.containsKey(chatId))						//polling user`s message from queue
				message = requestQueue.get(chatId).poll();
		while (message == null);

		return botService.buildResponse(chatId, message);				//build response for it
	}
}