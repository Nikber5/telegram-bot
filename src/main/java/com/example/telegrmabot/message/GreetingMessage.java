package com.example.telegrmabot.message;

import com.example.telegrmabot.util.ButtonUtil;
import com.example.telegrmabot.markup.TelegramMarkup;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class GreetingMessage extends TelegramMessage {
    public GreetingMessage(TelegramMarkup telegramMarkup) {
        super(telegramMarkup);
    }

    @Override
    public SendMessage createMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setReplyMarkup(telegramMarkup.getMarkup());
        response.setChatId(String.valueOf(message.getChatId()));
        response.setText("Hello, " + message.getFrom().getFirstName());
        return response;
    }

    @Override
    public String getText() {
        return ButtonUtil.DEFAULT;
    }
}
