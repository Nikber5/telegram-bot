package com.example.telegrmabot.strategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.telegrmabot.message.TelegramMessage;
import com.example.telegrmabot.message.YoutubeResponseMessage;
import com.example.telegrmabot.util.ButtonUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageStrategy {
    private final Map<String, TelegramMessage> messageMap;
    private final YoutubeResponseMessage youtubeResponseMessage;
    private String previousMessage;

    public MessageStrategy(List<TelegramMessage> messages, YoutubeResponseMessage youtubeResponseMessage) {
        this.messageMap = createStrategy(messages);
        this.youtubeResponseMessage = youtubeResponseMessage;
    }

    public SendMessage getMessage(Message message) {
        if (previousMessage != null && previousMessage.equals("Find video in youtube")) {
            previousMessage = message.getText();
            return youtubeResponseMessage.createMessage(message);
        }
        previousMessage = message.getText();
        TelegramMessage response = messageMap.getOrDefault(message.getText(), messageMap.get(ButtonUtil.DEFAULT));

        return response.createMessage(message);
    }

    private Map<String, TelegramMessage> createStrategy(List<TelegramMessage> messages) {
        return messages.stream().collect(Collectors.toMap(TelegramMessage::getText, m -> m));
    }
}
