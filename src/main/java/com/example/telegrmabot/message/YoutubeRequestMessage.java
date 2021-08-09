package com.example.telegrmabot.message;

import java.util.List;
import java.util.stream.Collectors;
import com.example.telegrmabot.markup.TelegramMarkup;
import com.example.telegrmabot.model.YouTubeItem;
import com.example.telegrmabot.util.ButtonUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class YoutubeRequestMessage extends TelegramMessage {
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
        return ButtonUtil.YOUTUBE;
    }
}
