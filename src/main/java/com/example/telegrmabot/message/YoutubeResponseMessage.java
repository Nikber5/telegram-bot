package com.example.telegrmabot.message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.telegrmabot.markup.TelegramMarkup;
import com.example.telegrmabot.model.YouTubeItem;
import com.example.telegrmabot.service.YouTubeApiService;
import com.example.telegrmabot.util.ButtonUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class YoutubeMessage extends TelegramMessage {
    private final YouTubeApiService youTubeApiService;

    public YoutubeMessage(TelegramMarkup telegramMarkup, YouTubeApiService youTubeApiService) {
        super(telegramMarkup);
        this.youTubeApiService = youTubeApiService;
    }

    @Override
    public SendMessage createMessage(Message message) {
        List<YouTubeItem> youTubeItems = youTubeApiService.youTubeSearch(message.getText(), 1L);
        SendMessage sendMessage = new SendMessage();

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        row.add("Popa");
        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row);
        markup.setKeyboard(rows);
        sendMessage.setReplyMarkup(markup);
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(youTubeItems.stream().map(YouTubeItem::getUrl).collect(Collectors.joining("\n")));
        return sendMessage;
    }

    @Override
    public String getText() {
        return ButtonUtil.YOUTUBE;
    }
}
