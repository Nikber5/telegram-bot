package com.example.telegrmabot.service;

import java.util.List;
import com.example.telegrmabot.model.TelegramMessage;
import com.example.telegrmabot.model.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface UserService {
    User saveOrUpdate(Message message);

    List<TelegramMessage> getAllMessages(Message message);
}
