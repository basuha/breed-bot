package com.basuha.breed_bot.service;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.Response;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BreedService {

    private RestTemplate restTemplate;
    private Gson gson;

//    @Value("${breed-list-url}")
    private static final String BREED_LIST_URL = "https://dog.ceo/api/breeds/list/all";

    public BreedService() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
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

    public Response parseResponse(Message message) {
        return gson.fromJson(message.getText(), Response.class);
    }
}