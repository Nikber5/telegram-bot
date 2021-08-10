package com.example.telegrmabot.util;

import java.util.ArrayList;
import java.util.List;

public class ButtonUtil {
    public static final String SEND_LINK = "Send my link";
    public static final String YOUTUBE = "Find video in youtube";
    public static final String DEFAULT = "DefaultButton";

    public static List<String> getAll() {
        List<String> buttons = new ArrayList<>();
        buttons.add(SEND_LINK);
        buttons.add(YOUTUBE);
        return buttons;
    }
}
