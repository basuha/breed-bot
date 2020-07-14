package com.basuha.breed_bot.repository;

import com.basuha.breed_bot.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
