package com.example.telegrmabot.util;

import java.util.ArrayList;
import java.util.List;

public final class ButtonNamesUtil {
    public static final String SEND_LINK = "Send my link";
    public static final String YOUTUBE_REQUEST = "Find video in youtube";
    public static final String YOUTUBE_RESPONSE = "*#&&!!";
    public static final String GET_ALL_MESSAGES = "Get my messages";
    public static final String DEFAULT = "DefaultButton";

    private ButtonNamesUtil() {
    }

    public static List<String> getAllButtons() {
        List<String> buttons = new ArrayList<>();
        buttons.add(SEND_LINK);
        buttons.add(YOUTUBE_REQUEST);
        buttons.add(GET_ALL_MESSAGES);
        return buttons;
    }
}
