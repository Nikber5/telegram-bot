package com.example.telegrmabot.repository;

import com.example.telegrmabot.model.TelegramMessage;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<TelegramMessage, Long> {
}
