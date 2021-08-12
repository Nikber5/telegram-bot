package com.example.telegrmabot.service;

import java.util.List;
import java.util.Optional;
import com.example.telegrmabot.model.TelegramMessage;
import com.example.telegrmabot.model.User;
import com.example.telegrmabot.repository.MessageRepository;
import com.example.telegrmabot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public UserServiceImpl(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public User saveOrUpdate(Message message) {
        TelegramMessage telegramMessage = new TelegramMessage(message.getText());
        messageRepository.save(telegramMessage);
        org.telegram.telegrambots.meta.api.objects.User messageFrom = message.getFrom();
        Optional<User> userOptional = userRepository.findByUserName(messageFrom.getUserName());
        User user;
        if (userOptional.isEmpty()) {
            user = userRepository.save(new User(messageFrom.getFirstName(),
                    messageFrom.getUserName(), List.of(telegramMessage)));
        } else {
            user = userOptional.get();
            List<TelegramMessage> messages = user.getMessages();
            messages.add(telegramMessage);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public List<TelegramMessage> getAllMessages(Message message) {
        String userName = message.getFrom().getUserName();
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("There is no user with username: " + userName);
        }
        return userOptional.get().getMessages();
    }
}
