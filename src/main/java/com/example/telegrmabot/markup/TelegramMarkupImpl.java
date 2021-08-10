package com.example.telegrmabot.markup;

import java.util.ArrayList;
import java.util.List;
import com.example.telegrmabot.util.ButtonNamesUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class TelegramMarkupImpl implements TelegramMarkup {
    @Override
    public ReplyKeyboardMarkup getMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        for (String button : ButtonNamesUtil.getAllButtons()) {
            row.add(button);
        }
        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }
}
