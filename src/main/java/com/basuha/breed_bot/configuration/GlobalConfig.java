package com.basuha.breed_bot.configuration;

import com.basuha.breed_bot.message.breed_list.BreedList;
import com.basuha.breed_bot.message.breed_list.BreedListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
            add("Here it is. Your image:");
            add("Here it is. Your photo:");
            add("Please, your image:");
            add("Please, your photo:");
            add("Look what i found!");
            add("Hope you like this:");
            add("It was hard, but i found:");
            add("It`s dangerous to go alone, take this &#128540;");
            add("How are you think about this?");
            add("Not at all!");
            add("Hope it will be useful:");
            add("Catch!");
        }};
    }

    @Bean
    public List<String> keyWords() {
        return new ArrayList<>(){{
            add("list");
            add("random");
            add("hello");
            add("help");
        }};
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @SneakyThrows
    public List<String> breedList() {
        BreedListResponse breedListResponse = gson().fromJson(
                restTemplate().getForObject(breedListUrl, String.class), BreedListResponse.class);

        BreedList breedList = breedListResponse.getMessage();

        List<String> outputList = new ArrayList<>();
        Field[] fields = breedList.getClass().getFields();
        for (Field breed : fields) {

            Field field = breedList.getClass().getDeclaredField(breed.getName());
            List<String> value = (List<String>) field.get(breedList);

            if (value.isEmpty())
                outputList.add(breed.getName());

            for (Object d : value)
                outputList.add(d + " " + breed.getName());
        }
        return outputList;
    }

}
