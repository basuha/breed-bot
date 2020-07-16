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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("keyWords")
    private List<String> KEYWORDS;

    @Autowired
    @Qualifier("breedList")
    private List<String> BREEDLIST;

    @Value("${random-image-url}")
    private String randomImageUrl;

    @Value("${random-image-of-breed-url}")
    private String randomImageOfBreedUrl;

    @Value("${message-parse-regex}")
    private String messageParseRegex;

    @Value("${message-split-regex}")
    private String messageSplitRegex;

    public String getPlainJSON(String URL) {
        return restTemplate.getForObject(URL, String.class);
    }

    public void getBreedList() {
//        breeds.forEach(System.out::println);
//
//        Pattern pattern = Pattern.compile("\"[a-z]\"");
//        Matcher matcher = pattern.matcher(breedListJson);
//        System.out.println("    ");
//
//        matcher.results().forEach(s -> System.out.println(s.group()));
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
            if(KEYWORDS.contains(s)) {
                parsedKeywords.add(s);
            }
        }

        return parsedKeywords;
    }
}