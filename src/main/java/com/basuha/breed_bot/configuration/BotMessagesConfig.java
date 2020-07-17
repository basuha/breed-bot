package com.basuha.breed_bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BotMessagesConfig {
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
            add("hi");
            add("help");
        }};
    }

    @Bean
    public List<String> greetingMessages() {
        return new ArrayList<>(){{
            add("Hi, %s!");
            add("Hi!");
            add("Good afternoon!");
            add("Good afternoon, %s!");
            add("Hello!");
            add("Hello, %s!");
            add("How are you?");
            add("How can i help you?");
        }};
    }

    @Bean
    public String botWelcomeMessage() {
        return "Hello, %s! My name is Breed Bot! &#128021;<br> Nice to meet you!<br>";
    }

    @Bean
    public String botHelpMessage() {
        return "I can show you random picture of any dog.<br>"
                + "You can type <b>list</b> to see the breeds list,<br>"
                + "and then choose what breed you want to see.<br>"
                + "Or just type <b>random</b> to get random image of random breed.<br>"
                + "Please notice, <b>you can type multiple keyword in one request!</b><br><br>"
                + "If you forgot something, just type <b>help</b>";
    }
}
