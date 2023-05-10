package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public interface BotService {
    SendMessage register(String chatId);

    SendMessage menu(String chatId);
    SendPhoto europeMenu(String chatId);
    SendPhoto asiaMenu(String chatId);
    SendPhoto africaMenu(String chatId);
    SendPhoto americaMenu(String chatId);
    SendPhoto france(String chatId);
    SendMessage replyKeyboardRemove();
}
