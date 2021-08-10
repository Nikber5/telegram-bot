package com.example.telegrmabot.message;

import com.example.telegrmabot.markup.TelegramMarkup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class AbstractTelegramMessage {
    protected TelegramMarkup telegramMarkup;

    public AbstractTelegramMessage(TelegramMarkup telegramMarkup) {
        this.telegramMarkup = telegramMarkup;
    }

    public abstract SendMessage createMessage(Message message);

    public abstract String getText();
}
