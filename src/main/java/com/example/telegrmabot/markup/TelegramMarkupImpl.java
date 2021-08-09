package com.example.telegrmabot.markup;

import java.util.ArrayList;
import java.util.List;
import com.example.telegrmabot.util.ButtonUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class TelegramMarkupImpl implements TelegramMarkup {
    @Override
    public ReplyKeyboardMarkup getMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        row.add(ButtonUtil.SEND_LINK);
        row.add("Say hello");
        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }
}
