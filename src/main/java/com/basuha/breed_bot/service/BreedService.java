package com.basuha.breed_bot.service;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BreedService {

    @Value("${breed-list-url}")
    private String breedListUrl;

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
    @Qualifier("breedList")
    private List<String> breedList;

    @Autowired
    @Qualifier("randomImageResponseBotTexts")
    private List<String> botTexts;

    @Value("${random-image-url}")
    private String randomImageUrl;

    @Value("${random-image-of-breed-url}")
    private String randomImageOfBreedUrl;

    @Value("${message-parse-regex}")
    private String messageParseRegex;

    @Value("${message-split-regex}")
    private String messageSplitRegex;

    public String getRandomDogImage(){
        return getPlainJSON(randomImageUrl);
    }

    public String getRandomBotText(){
        return botTexts.get(new Random().nextInt(botTexts.size()));
    }

    public String getPlainJSON(String URL) {
        return restTemplate.getForObject(URL, String.class);
    }

    public String getBreedList() {
        return getPlainJSON(breedListUrl);
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

    public List<String> parseUserMessage(String message) {
        List<String> parsedKeywords = new ArrayList<>();

        for (var s : message.toLowerCase()
                .replaceAll(messageParseRegex, "")
                .replaceAll(messageSplitRegex, " ")
                .split(" ")) {
            if(keywords.contains(s)) {
                parsedKeywords.add(s);
            }
        }

        return parsedKeywords;
    }
}