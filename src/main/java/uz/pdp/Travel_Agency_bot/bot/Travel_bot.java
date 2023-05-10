package uz.pdp.Travel_Agency_bot.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;

import java.util.Optional;

import static uz.pdp.Travel_Agency_bot.util.BeanUtil.botService;
import static uz.pdp.Travel_Agency_bot.util.BeanUtil.userService;

public class Travel_bot extends TelegramLongPollingBot {

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            Long chatId = message.getChatId();

            Optional<User> currentUser = userService.getUserByChatId(chatId);
            UserState userState = UserState.START;

            if (currentUser.isPresent()) {
                userState = currentUser.get().getState();
                switch (userState) {
                    case REGISTERED, MENU -> {
                        switch (text) {
                            case "EUROPE" -> {
                                userService.updateState(chatId, UserState.EUROPE);
                                userState = UserState.EUROPE;
                            }
                            case "ASIA" -> {
                                userService.updateState(chatId, UserState.ASIA);
                                userState = UserState.ASIA;
                            }
                            case "AFRICA" -> {
                                userService.updateState(chatId, UserState.AFRICA);
                                userState = UserState.AFRICA;
                            }
                            case "AMERICA" -> {
                                userService.updateState(chatId, UserState.AMERICA);
                                userState = UserState.AMERICA;
                            }
                        }
                    }
                }

            } else if (message.hasContact()) {
                Contact contact = message.getContact();

                User user = User.builder()
                        .chatId(chatId)
                        .firstName(contact.getFirstName())
                        .lastName(contact.getLastName())
                        .phoneNumber(contact.getPhoneNumber())
                        .state(UserState.REGISTERED)
                        .build();
                userService.add(user);
                userState = UserState.REGISTERED;

                execute(new SendMessage(chatId.toString(), "Successfully registered!"));
            }

            switch (userState) {
                case START -> execute(botService.register(chatId.toString()));
                case REGISTERED, MENU -> execute(botService.menu(chatId.toString()));
                case EUROPE -> {
                    execute(botService.europeMenu(chatId.toString()));
                    userService.updateState(chatId, UserState.MENU);
                }
                case ASIA -> {
                    execute(botService.asiaMenu(chatId.toString()));
                    userService.updateState(chatId, UserState.MENU);
                }
                case AFRICA -> {
                    execute(botService.africaMenu(chatId.toString()));
                    userService.updateState(chatId, UserState.MENU);
                }
                case AMERICA -> {
                    execute(botService.americaMenu(chatId.toString()));
                    userService.updateState(chatId, UserState.MENU);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "G23_Travel_bot";
    }

    @Override
    public String getBotToken() {
        return "6283576225:AAHlNdPkFmrGR5wXD5QgQt4-MvTGJQVqHYQ";
    }
}
