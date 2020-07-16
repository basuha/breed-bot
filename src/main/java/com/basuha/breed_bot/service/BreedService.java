package com.basuha.breed_bot.service;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.basuha.breed_bot.message.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
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

//    @Value("${breed-list-url}") //TODO:to config file

    private static final String RANDOM_IMAGE = "https://dog.ceo/api/breeds/image/random";
    private static final String RANDOM_IMAGE_OF_BREED = "https://dog.ceo/api/breed/%s/images/random";
    private static final String PARSE_REGEX = "([?!:;,.)(])";
    private static final String SPLIT_REGEX = "([\\-`_])";

    @Autowired
    @Qualifier("keyWords")
    private List<String> KEYWORDS;

    @Autowired
    @Qualifier("breedList")
    private List<String> BREEDLIST;

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

        for (var s : message
                .replaceAll(PARSE_REGEX, "")
                .replaceAll(SPLIT_REGEX, " ")
                .split(" ")) {
            if(KEYWORDS.contains(s)) {
                parsedKeywords.add(s);
            }
        }

        return parsedKeywords;
    }
}