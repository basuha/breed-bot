package com.basuha.breed_bot.service;

import com.basuha.breed_bot.message.BreedList;
import com.basuha.breed_bot.message.BreedListResponse;
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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class BreedService {

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

    @Value("${breed-list-url}")
    private String breedListUrl;

    @Value("${message-parse-regex}")
    private String messageParseRegex;

    @Value("${message-split-regex}")
    private String messageSplitRegex;

    public String getRandomDogImage() {
        return getPlainJSON(randomImageUrl);
    }

    public String getRandomBotText() {
        return botTexts.get(new Random().nextInt(botTexts.size()));
    }

    public String getPlainJSON(String URL) {
        return restTemplate.getForObject(URL, String.class);
    }

    public List<String> getBreedList() {
        BreedListResponse breedListResponse = gson.fromJson(getPlainJSON(breedListUrl), BreedListResponse.class);
        BreedList breedList = breedListResponse.getMessage();
        List<String> outputList = new ArrayList<>();
        for (var breed : breedList.getClass().getFields()) {
            Field field = null;
            try {
                field = breedList.getClass().getDeclaredField(breed.getName());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            try {
                List<String> value = (List<String>) field.get(breedList);
                if (value.isEmpty()) {
                    outputList.add(breed.getName());
                }
                for (var d : value) {
                    outputList.add(d + " " + breed.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return outputList;
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