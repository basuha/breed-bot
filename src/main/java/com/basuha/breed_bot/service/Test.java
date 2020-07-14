package com.basuha.breed_bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

@Service
public class Test {
    public static void main(String[] args) {
        RestService restService = new RestService(new RestTemplateBuilder());
        System.out.println(restService.getBreedListPlainJSON());
    }
}
