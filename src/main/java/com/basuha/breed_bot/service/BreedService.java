package com.basuha.breed_bot.service;

import com.basuha.breed_bot.message.Message;
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

    public List<String> parseUserMessage(String message) {
        List<String> parsedKeywords = new ArrayList<>();
        List<String> parsed = Arrays.asList(message.toLowerCase()
                .replaceAll(messageParseRegex, "")
                .replaceAll(messageSplitRegex, " ")
                .split(" "));
        ListIterator<String> iterator = parsed.listIterator();
        System.out.println(parsed.size());
            while (iterator.hasNext()) { //TODO: improve algorithm
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
                        }
                    }
                }
            }

        return parsedKeywords;
    }
}