package com.basuha.breed_bot.service;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.repository.MessageRepo;
import com.basuha.breed_bot.repository.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BotService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("keyWords")
    private List<String> keywords;

    @Autowired
    @Qualifier("greetingMessages")
    private List<String> greetingMessages;

    @Autowired
    @Qualifier("breedList")
    private List<String> breedList;

    @Autowired
    @Qualifier("randomImageResponseBotTexts")
    private List<String> botTexts;

    @Autowired
    @Qualifier("botWelcomeMessage")
    private String botWelcomeMessage;

    @Autowired
    @Qualifier("botHelpMessage")
    private String botHelpMessage;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepo userRepo;


    @Value("${random-image-url}")
    private String randomImageUrl;

    @Value("${random-image-of-breed-url}")
    private String randomImageOfBreedUrl;

    @Value("${random-image-of-sub-breed-url}")
    private String randomImageOfSubBreedUrl;

    @Value("${breed-list-url}")
    private String breedListUrl;

    @Value("${message-parse-regex}")
    private String messageParseRegex;

    @Value("${message-split-regex}")
    private String messageSplitRegex;

    public String getBotWelcomeMessage(){
        return botWelcomeMessage + botHelpMessage;
    }

    public String getBotHelpMessage(){
        return botHelpMessage;
    }

    public String getRandomDogImage() {
        return getPlainJSON(randomImageUrl);
    }

    public String getRandomDogImageByBreed(String breed) {
        String url;
        if (breed.contains(" ")) {
            String[] spliced = breed.split(" ");
            url = String.format(randomImageOfSubBreedUrl, spliced[1], spliced[0]);
            System.out.println(url);
        } else {
            url = String.format(randomImageOfBreedUrl, breed);
            System.out.println(url);
        }
        return getPlainJSON(url);
    }

    public String getRandomBotText() {
        return botTexts.get(new Random().nextInt(botTexts.size()));
    }

    public String getRandomBotGreetingMessage() {
        return greetingMessages.get(new Random().nextInt(greetingMessages.size()));
    }

    public String getPlainJSON(String URL) {
        return restTemplate.getForObject(URL, String.class);
    }

    public String getBreedListJson() {
        return gson.toJson(breedList);
    }

    public Message jsonToMessage(String json) {
        System.out.println(json);
        Message message = new Message();
        try {
            message = objectMapper.readValue(json, Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Response parseResponse(Message message) {
        return gson.fromJson(message.getData(), Response.class);
    }

    private List<String> parseMessage(String message) {
        List<String> parsedKeywords = new ArrayList<>();
        List<String> parsed = Arrays.asList(message.toLowerCase()
                .replaceAll(messageParseRegex, "")
                .replaceAll(messageSplitRegex, " ")
                .split(" "));
        ListIterator<String> iterator = parsed.listIterator();
        System.out.println(parsed.size());
            while (iterator.hasNext()) {
                String s = iterator.next();
                if (keywords.contains(s)) {
                    parsedKeywords.add(s);
                    continue;
                }

                if (breedList.contains(s)) {
                    parsedKeywords.add(s);
                    continue;
                }

                if (iterator.hasNext()) {
                    String next = iterator.next();
                    if (breedList.contains(s + " " + next)) {
                        parsedKeywords.add(s + " " + next);
                    }
                }

                for (String breed : breedList) {
                    if (breed.contains(" ")) {
                        String mainBreed = breed.split(" ")[1];
                        if (mainBreed.equals(s)) {
                            parsedKeywords.add(s);
                            break;
                        }
                    }
                }
            }

        return parsedKeywords;
    }

    public List<Message> buildResponse(Long chatId, Message message) {
        List<Message> requests = new ArrayList<>();
        List<Message> responses = new ArrayList<>();
        if (message.getText() != null) {
            List<String> parsedKeyWords = parseMessage(message.getText());
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
                        response.setData(getBreedListJson());
                        response.setType("list");
                    }
                    case "random" -> {
                        response.setData(getRandomDogImage());
                        response.setText(getRandomBotText());
                        response.setType("image");
                    }
                    case "hello","hi" ->
                            response.setText(String.format(
                                    getRandomBotGreetingMessage(),
                                    userRepo.findById(chatId).get().getUsername()));

                    case "help" ->
                            response.setText(getBotHelpMessage());

                    default -> {
                        response.setData(getRandomDogImageByBreed(request.getKeyword()));
                        response.setText(getRandomBotText()
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