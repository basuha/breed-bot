package com.basuha.breed_bot.repository;

import com.basuha.breed_bot.message.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String s);
}
