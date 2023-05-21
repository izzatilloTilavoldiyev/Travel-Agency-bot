package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.model.UserState;

public interface BotService {
    SendMessage baseMenuButton(String chatId);
    SendMessage register(String chatId);

    SendMessage menu(String chatId);

    SendPhoto countryMenu(String chatId, Long continent_id, UserState state);

    SendPhoto countryServiceButtons(String chatId, String country);
    InlineKeyboardMarkup buyTicketButtons();

    SendPhoto transportMenu(String chatId);

    SendMessage countryInfo(String chatId, String country);

    SendMessage replyKeyboardRemove(String chatId);

    Long getContinentId(String continent);
    SendMessage userAccountButtons(String chatId);
    SendMessage cardMenuButtons(Card card, String chatId);
    SendMessage display_1_button(String chatId, String description, String value);
}
