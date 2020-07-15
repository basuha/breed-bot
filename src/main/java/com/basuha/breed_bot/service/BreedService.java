package com.basuha.breed_bot.service;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.basuha.breed_bot.message.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BreedService {

    private RestTemplate restTemplate;
    private Gson gson;
    private ObjectMapper objectMapper;

//    @Value("${breed-list-url}")
    private static final String BREED_LIST_URL = "https://dog.ceo/api/breeds/list/all";

    public BreedService() {
        this.restTemplate = new RestTemplate();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.objectMapper = new ObjectMapper();
    }

    private String getBreedListPlainJSON() {
        return restTemplate.getForObject(BREED_LIST_URL, String.class);
    }

    public String getPlainJSON(String URL) {
        return restTemplate.getForObject(URL, String.class);
    }

    public List<String> getBreedList() {
        String breedListJson = getBreedListPlainJSON();
        System.out.println(breedListJson);
        List<String> breeds = Arrays.asList(breedListJson.split("[^a-z]+"));
//        breeds.forEach(System.out::println);
//
//        Pattern pattern = Pattern.compile("\"[a-z]\"");
//        Matcher matcher = pattern.matcher(breedListJson);
//        System.out.println("    ");
//
//        matcher.results().forEach(s -> System.out.println(s.group()));
        return breeds;
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
}