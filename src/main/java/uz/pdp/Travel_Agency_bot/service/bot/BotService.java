package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import uz.pdp.Travel_Agency_bot.model.UserState;

public interface BotService {
    SendMessage register(String chatId);

    SendMessage menu(String chatId);

    SendPhoto countryMenu(String chatId, Long continent_id, UserState state);

    SendPhoto country(String chatId, String country);

    SendMessage replyKeyboardRemove();

    SendMessage transportMenu(String chatId);
}
