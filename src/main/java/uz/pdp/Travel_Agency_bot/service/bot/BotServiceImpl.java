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
    public SendPhoto countryServiceButtons(String chatId, String country) {

        BaseUtils.countries.remove(chatId);
        BaseUtils.countries.put(chatId, country);

        return SendPhoto.builder()
                .chatId(chatId)
                .caption(country)
                .replyMarkup(inlineButtons.countryServiceButtons())
                .photo(new InputFile(new File(BASE_URL + country.toLowerCase() + ".jpg")))
                .build();
    }

    @Override
    public SendPhoto transportMenu(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .caption("Choose transport")
                .replyMarkup(inlineButtons.transportButtons(countryService.transportsDB()))
                .photo(new InputFile(new File(BASE_URL + "transports.jpg")))
                .build();
    }

    @Override
    public SendMessage countryInfo(String chatId, String country) {
        SendMessage sendMessage = new SendMessage();
        String info = countryService.countryInfoDB(country);
        sendMessage.setChatId(chatId);
        sendMessage.setText(info);
        return sendMessage;
    }

    @Override
    public SendMessage replyKeyboardRemove(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "choose");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;
    }
}
