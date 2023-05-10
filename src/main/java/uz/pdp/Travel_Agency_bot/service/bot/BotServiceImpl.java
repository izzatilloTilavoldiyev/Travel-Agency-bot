package uz.pdp.Travel_Agency_bot.service.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.io.File;

public class BotServiceImpl implements BotService{

    ReplyButtons replyButtons = new ReplyButtons();
    InlineButtons inlineButtons = new InlineButtons();

    @Override
    public SendMessage register(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Please send your number");
        sendMessage.setReplyMarkup(replyButtons.shareContactButton());
        return sendMessage;
    }

    @Override
    public SendMessage menu(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Menu");
        sendMessage.setReplyMarkup(replyButtons.menuButtons());
        return sendMessage;
    }

    @Override
    public SendPhoto europeMenu(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .caption("Europe countries")
                .replyMarkup(inlineButtons.europeButtons())
                .photo(new InputFile(new File("C:\\Java_OOP_Projects\\Travel_Agency_bot\\pictures\\europe.jpg")))
                .build();
    }

    @Override
    public SendPhoto asiaMenu(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .caption("ASIA countries")
                .replyMarkup(inlineButtons.asiaButtons())
                .photo(new InputFile(new File("C:\\Java_OOP_Projects\\Travel_Agency_bot\\pictures\\asia.jpg")))
                .build();
    }

    @Override
    public SendPhoto africaMenu(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .caption("AFRICA countries")
                .replyMarkup(inlineButtons.africaButtons())
                .photo(new InputFile(new File("C:\\Java_OOP_Projects\\Travel_Agency_bot\\pictures\\africa.jpg")))
                .build();
    }

    @Override
    public SendPhoto americaMenu(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .caption("AMERICA countries")
                .replyMarkup(inlineButtons.americaButtons())
                .photo(new InputFile(new File("C:\\Java_OOP_Projects\\Travel_Agency_bot\\pictures\\america.jpg")))
                .build();
    }

    @Override
    public SendPhoto france(String chatId) {
        return SendPhoto.builder()
                .chatId(chatId)
                .caption("FRANCE")
                .replyMarkup(inlineButtons.franceButtons())
                .photo(new InputFile(new File("C:\\Java_OOP_Projects\\Travel_bot\\pictures\\france.jpg")))
                .build();
    }
    @Override
    public SendMessage replyKeyboardRemove() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;
    }
}
