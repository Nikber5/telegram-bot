package com.example.telegrmabot.message;

import com.example.telegrmabot.markup.TelegramMarkup;
import com.example.telegrmabot.util.ButtonNamesUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class YoutubeRequestMessage extends AbstractTelegramMessage {
    public YoutubeRequestMessage(TelegramMarkup telegramMarkup) {
        super(telegramMarkup);
    }

    @Override
    public SendMessage createMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setReplyMarkup(telegramMarkup.getMarkup());
        response.setChatId(String.valueOf(message.getChatId()));
        response.setText("Please enter a request: ");
        return response;
    }

    @Override
    public String getText() {
        return ButtonNamesUtil.YOUTUBE_REQUEST;
    }
}
