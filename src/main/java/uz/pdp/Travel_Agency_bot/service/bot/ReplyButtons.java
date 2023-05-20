package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static uz.pdp.Travel_Agency_bot.util.Constants.*;

public class ReplyButtons {

    public ReplyKeyboardMarkup shareContactButton() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);

        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("☎"+Share_contact);
        button.setRequestContact(true);
        row.add(button);

        replyKeyboardMarkup.setKeyboard(Collections.singletonList(row));
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup baseMenuButtons() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(Travel);
        rows.add(row);

        row = new KeyboardRow();
        row.add(My_account);
        rows.add(row);

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup menuButtons(ArrayList<String> continentDB) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        for (String s : continentDB) {
            row.add(s);
            if (row.size() == 2) {
                rows.add(row);
                row = new KeyboardRow();
            }
        }
        rows.add(row);
        row = new KeyboardRow();
        row.add(BASE_MENU);
        rows.add(row);
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup userAccountButtons() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(My_cards);
        row.add(My_tickets);
        rows.add(row);

        row = new KeyboardRow();
        row.add(Balance);
        row.add(History);
        rows.add(row);

        row = new KeyboardRow();
        row.add(BASE_MENU);
        rows.add(row);

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

}
