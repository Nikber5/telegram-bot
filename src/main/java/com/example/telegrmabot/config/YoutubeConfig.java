package com.example.telegrmabot.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeConfig {
    @Value(value = "${youtube.application}")
    private String YOUTUBE_API_APPLICATION;

    @Bean
    public YouTube getYoutube() {
        try {
            return new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), new GsonFactory(),
                    null).setApplicationName(YOUTUBE_API_APPLICATION).build();
        } catch (Exception e) {
            throw new RuntimeException("Can't create Youtube instance", e);
        }
    }
}
