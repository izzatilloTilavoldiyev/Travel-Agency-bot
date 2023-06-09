package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.service.card.CardService;
import uz.pdp.Travel_Agency_bot.service.card.CardServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.Travel_Agency_bot.util.Constants.*;

public class InlineButtons {
    CardService cardService = new CardServiceImpl();
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
        String sticker = "";
        for (String s : countriesDB) {
            switch (s) {
                case Bus -> sticker = "\uD83D\uDE8C";
                case Train -> sticker = "\uD83D\uDE86";
                case Plane -> sticker = "\uD83D\uDEE9";
            }
            InlineKeyboardButton button = new InlineKeyboardButton(sticker + s);
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

        InlineKeyboardButton button = new InlineKeyboardButton("\uD83C\uDFAB"+Buy_ticket);
        button.setCallbackData(Buy_ticket);
        row.add(button);

        button = new InlineKeyboardButton("\uD83D\uDD0E"+More_info);
        button.setCallbackData(More_info);
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton(MENU);
        button.setCallbackData(MENU);
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup buyTicketButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton("1");
        button.setCallbackData("1");
        row.add(button);

        button = new InlineKeyboardButton("0");
        button.setCallbackData("0");
        row.add(button);

        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup cardMenuButtons(Card card) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton("⭐  "+card.getCard_number()+"  ➡"+card.getBrand());
        button.setCallbackData(String.valueOf(card.getCard_id()));
        row.add(button);
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createCardButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton("➕"+Create_card);
        button.setCallbackData(Create_card);
        row.add(button);
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup display_1_button(String value) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        String sticker = "";
        switch (value) {
            case Fill_balance -> sticker = "✅";
            case Delete_tickets, Clean_histories -> sticker = "\uD83D\uDEAB";
        }
        InlineKeyboardButton button = new InlineKeyboardButton(sticker+value);
        button.setCallbackData(value);
        row.add(button);

        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

}
