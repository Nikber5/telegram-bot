package com.example.telegrmabot.message;

import java.util.List;
import java.util.stream.Collectors;
import com.example.telegrmabot.markup.TelegramMarkup;
import com.example.telegrmabot.model.TelegramMessage;
import com.example.telegrmabot.service.UserService;
import com.example.telegrmabot.util.ButtonNamesUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class GetAllMessagesMessage extends AbstractTelegramMessage {
    private final UserService userService;

    public GetAllMessagesMessage(TelegramMarkup telegramMarkup, UserService userService) {
        super(telegramMarkup);
        this.userService = userService;
    }

    @Override
    public SendMessage createMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setReplyMarkup(telegramMarkup.getMarkup());
        response.setChatId(String.valueOf(message.getChatId()));
        List<TelegramMessage> messages = userService.getAllMessages(message);
        response.setText(messages
                .stream()
                .map(TelegramMessage::getValue)
                .collect(Collectors.joining("\n")));
        return response;
    }

    @Override
    public String getText() {
        return ButtonNamesUtil.GET_ALL_MESSAGES;
    }
}
