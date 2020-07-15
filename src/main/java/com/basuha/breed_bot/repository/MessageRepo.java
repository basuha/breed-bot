package com.basuha.breed_bot.repository;

import com.basuha.breed_bot.message.Message;
import com.basuha.breed_bot.message.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> getByAuthor(User user);
}
