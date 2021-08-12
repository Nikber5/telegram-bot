package com.example.telegrmabot.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String userName;
    @OneToMany(fetch = FetchType.EAGER)
    private List<TelegramMessage> messages;

    public User() {
    }

    public User(String firstName, String userName, List<TelegramMessage> messages) {
        this.firstName = firstName;
        this.userName = userName;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<TelegramMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<TelegramMessage> message) {
        this.messages = message;
    }
}
