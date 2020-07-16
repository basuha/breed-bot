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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class GlobalConfig {
    @Value("${breed-list-url}")
    private String breedListUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public List<String> randomImageResponseBotTexts() {
        return new ArrayList<>() {{
            add("Here it is. Your image");
            add("Here it is. Your photo");
            add("Please, your image");
            add("Please, your photo");
            add("Look what i found!");
            add("Hope you like this");
            add("It was hard, but i found");
            add("It`s dangerous to go alone, take this &#128540;");
            add("How are you think about this?");
            add("Not at all!");
            add("Hope it will be useful");
            add("Catch!");
        }};
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
        String breedListJson = restTemplate().getForObject(breedListUrl, String.class);
//        String s = breedListJson.replaceAll(":\", " ");
////        Pattern pattern = Pattern.compile("\"(.+?)\"");
//        Pattern pattern = Pattern.compile("\"(.+?)\"");
//        Matcher matcher = pattern.matcher(breedListJson);
//        while (matcher.find())
//            System.out.println(matcher.group());
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
