package com.basuha.breed_bot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class GlobalConfig {
    @Value("${breed-list-url}")
    private String BREED_LIST_URL;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public List<String> breedList() {
        String breedListJson = restTemplate().getForObject(BREED_LIST_URL, String.class);
        return Arrays.asList(breedListJson.split("[^a-z]+"));
    }

    @Bean
    public List<String> keyWords() {
        return new ArrayList<>(){{
            add("list");
            add("random");
            add("hello");
        }};
    }

}
