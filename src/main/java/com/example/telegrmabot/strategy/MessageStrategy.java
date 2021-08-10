package com.example.telegrmabot.strategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.telegrmabot.message.TelegramMessage;
import com.example.telegrmabot.util.ButtonNamesUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageStrategy {
    private final Map<String, TelegramMessage> messageMap;
    private String previousMessage;

    public MessageStrategy(List<TelegramMessage> messages) {
        this.messageMap = createStrategy(messages);
    }

    public SendMessage getMessage(Message message) {
        String messageText = message.getText();
        if (previousMessage != null && previousMessage.equals(ButtonNamesUtil.YOUTUBE_REQUEST)) {
            messageText = ButtonNamesUtil.YOUTUBE_RESPONSE;
        }
        previousMessage = message.getText();
        TelegramMessage response = messageMap.getOrDefault(messageText, messageMap.get(ButtonNamesUtil.DEFAULT));

        return response.createMessage(message);
    }

    private Map<String, TelegramMessage> createStrategy(List<TelegramMessage> messages) {
        return messages.stream().collect(Collectors.toMap(TelegramMessage::getText, m -> m));
    }
}
