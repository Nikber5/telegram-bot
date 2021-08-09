package com.example.telegrmabot.handler;

import com.example.telegrmabot.message.YoutubeResponseMessage;
import com.example.telegrmabot.strategy.MessageStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyBot extends TelegramLongPollingBot {
    private final MessageStrategy messageStrategy;
    private final YoutubeResponseMessage youtubeResponseMessage;
    @Value(value = "${telegram.username}")
    private String username;
    @Value(value = "${telegram.token}")
    private String token;

    private String previousMessage;

    public MyBot(MessageStrategy messageStrategy, YoutubeResponseMessage youtubeResponseMessage) {
        this.messageStrategy = messageStrategy;
        this.youtubeResponseMessage = youtubeResponseMessage;
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

        SendMessage response;
        if (previousMessage != null && previousMessage.equals("Find video in youtube")) {
            response = youtubeResponseMessage.createMessage(message);
        } else {
            response = messageStrategy.getMessage(message);
        }

        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        previousMessage = message.getText();
    }
}
