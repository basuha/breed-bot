package com.basuha.breed_bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    @Autowired
    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getBreedListPlainJSON() {
        String url = "https://dog.ceo/api/breeds/list/all";
        return this.restTemplate.getForObject(url, String.class);
    }
}