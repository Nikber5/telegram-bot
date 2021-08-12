package com.example.telegrmabot.handler;

import com.example.telegrmabot.service.UserService;
import com.example.telegrmabot.strategy.MessageStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyBot extends TelegramLongPollingBot {
    private final Logger logger = LoggerFactory.getLogger(MyBot.class);

    @Value(value = "${telegram.username}")
    private String username;
    @Value(value = "${telegram.token}")
    private String token;

    private final MessageStrategy messageStrategy;
    private final UserService userService;

    public MyBot(MessageStrategy messageStrategy, UserService userService) {
        this.messageStrategy = messageStrategy;
        this.userService = userService;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        logger.info("Message: " + message + ", received");
        userService.saveOrUpdate(message);
        SendMessage response = messageStrategy.getMessage(message);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't send response message", e);
        }
    }
}
