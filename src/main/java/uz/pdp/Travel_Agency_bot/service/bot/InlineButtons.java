package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineButtons {
    public InlineKeyboardMarkup countryButtons(ArrayList<String> countriesDB) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (String s : countriesDB) {
            InlineKeyboardButton button = new InlineKeyboardButton(s);
            button.setCallbackData(s);
            row.add(button);
            if (row.size() == 2) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup transportButtons(ArrayList<String> countriesDB) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (String s : countriesDB) {
            InlineKeyboardButton button = new InlineKeyboardButton(s);
            button.setCallbackData(s);
            row.add(button);
            rows.add(row);
            row = new ArrayList<>();
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup countryServiceButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton("Buy ticket");
        button.setCallbackData("Buy ticket");
        row.add(button);

        button = new InlineKeyboardButton("More info");
        button.setCallbackData("More info");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("MENU");
        button.setCallbackData("MENU");
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
