package com.example.telegrmabot.message;

import java.util.List;
import java.util.stream.Collectors;
import com.example.telegrmabot.markup.TelegramMarkup;
import com.example.telegrmabot.model.YouTubeItem;
import com.example.telegrmabot.service.YouTubeApiService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class YoutubeResponseMessage {
    private final YouTubeApiService youTubeApiService;
    private final TelegramMarkup telegramMarkup;

    public YoutubeResponseMessage(TelegramMarkup telegramMarkup, YouTubeApiService youTubeApiService) {
        this.telegramMarkup = telegramMarkup;
        this.youTubeApiService = youTubeApiService;
    }

    public SendMessage createMessage(Message message) {
        List<YouTubeItem> youTubeItems = youTubeApiService.youTubeSearch(message.getText(), 1L);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(telegramMarkup.getMarkup());
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(youTubeItems.stream().map(YouTubeItem::getUrl).collect(Collectors.joining("\n")));
        return sendMessage;
    }
}
