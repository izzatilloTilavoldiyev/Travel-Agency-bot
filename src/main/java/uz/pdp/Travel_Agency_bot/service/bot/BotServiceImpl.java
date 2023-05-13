package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import uz.pdp.Travel_Agency_bot.model.UserState;
import uz.pdp.Travel_Agency_bot.util.BaseUtils;

import java.io.File;

public class BotServiceImpl implements BotService {

    public static final String BASE_URL = "C:\\Java_OOP_Projects\\Travel_Agency_bot\\pictures\\";
    ReplyButtons replyButtons = new ReplyButtons();
    InlineButtons inlineButtons = new InlineButtons();
    CountryService countryService = new CountryService();

    @Override
    public SendMessage register(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Please send your number");
        sendMessage.setReplyMarkup(replyButtons.shareContactButton());
        return sendMessage;
    }

    @Override
    public SendMessage menu(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Menu");
        sendMessage.setReplyMarkup(replyButtons.menuButtons(countryService.continentsDB()));
        return sendMessage;
    }

    @Override
    public SendPhoto countryMenu(String chatId, Long continent_id, UserState userState) {
        return SendPhoto.builder()
                .chatId(chatId)
                .caption(userState.name() + " countries ")
                .replyMarkup(inlineButtons.countryButtons(countryService.countyDB(continent_id)))
                .photo(new InputFile(new File(BASE_URL + userState.name().toLowerCase() + ".jpg")))
                .build();
    }

    @Override
    public SendPhoto country(String chatId, String country) {

        BaseUtils.countries.remove(chatId);
        BaseUtils.countries.put(chatId, country);

        String path = "";
        switch (country) {
            case "FRANCE" -> path = BASE_URL + "france.jpg";
            case "GERMANY" -> path = BASE_URL + "germany.jpg";
            case "SPAIN" -> path = BASE_URL + "spain.jpg";
            case "TURKEY" -> path = BASE_URL + "turkey.jpg";
            case "ITALY" -> path = BASE_URL + "italy.jpg";
        }
        return SendPhoto.builder()
                .chatId(chatId)
                .caption(country)
                .replyMarkup(inlineButtons.countryButtons())
                .photo(new InputFile(new File(path)))
                .build();
    }

    @Override
    public SendMessage transportMenu(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Choose transport");
        sendMessage.setReplyMarkup(replyButtons.transportMenu());
        return sendMessage;
    }

    @Override
    public SendMessage replyKeyboardRemove() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;
    }
}
