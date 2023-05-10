package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineButtons {

    public InlineKeyboardMarkup europeButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton("FRANCE");
        button.setCallbackData("FRANCE");
        row.add(button);

        button = new InlineKeyboardButton("GERMANY");
        button.setCallbackData("GERMANY");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("SPAIN");
        button.setCallbackData("SPAIN");
        row.add(button);

        button = new InlineKeyboardButton("TURKEY");
        button.setCallbackData("TURKEY");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("ITALY");
        button.setCallbackData("ITALY");
        row.add(button);

        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup asiaButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton("DUBAI");
        button.setCallbackData("DUBAI");
        row.add(button);

        button = new InlineKeyboardButton("MALAYSIA");
        button.setCallbackData("MALAYSIA");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("CHINA");
        button.setCallbackData("CHINA");
        row.add(button);

        button = new InlineKeyboardButton("JAPAN");
        button.setCallbackData("JAPAN");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("SAUDI ARABIA");
        button.setCallbackData("SAUDI ARABIA");
        row.add(button);

        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup africaButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton("KENYA");
        button.setCallbackData("KENYA");
        row.add(button);

        button = new InlineKeyboardButton("ZAMBIA");
        button.setCallbackData("ZAMBIA");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("TANZANIA");
        button.setCallbackData("TANZANIA");
        row.add(button);

        button = new InlineKeyboardButton("BOTSWANA");
        button.setCallbackData("BOTSWANA");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("SOUTH AFRICA");
        button.setCallbackData("SOUTH AFRICA");
        row.add(button);

        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup americaButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton("USA");
        button.setCallbackData("USA");
        row.add(button);

        button = new InlineKeyboardButton("BRAZIL");
        button.setCallbackData("BRAZIL");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("ARGENTINA");
        button.setCallbackData("ARGENTINA");
        row.add(button);

        button = new InlineKeyboardButton("MEXICO");
        button.setCallbackData("MEXICO");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton("CANADA");
        button.setCallbackData("CANADA");
        row.add(button);

        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup franceButtons() {
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
