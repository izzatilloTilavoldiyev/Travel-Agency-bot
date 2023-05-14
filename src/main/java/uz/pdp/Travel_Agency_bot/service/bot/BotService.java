package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import uz.pdp.Travel_Agency_bot.model.UserState;

public interface BotService {
    SendMessage register(String chatId);

    SendMessage menu(String chatId);

    SendPhoto countryMenu(String chatId, Long continent_id, UserState state);

    SendPhoto countryServiceButtons(String chatId, String country);

    SendPhoto transportMenu(String chatId);

    SendMessage countryInfo(String chatId, String country);

    SendMessage replyKeyboardRemove(String chatId);

    Long getContinentId(String continent);
}
