package uz.pdp.Travel_Agency_bot.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;
import uz.pdp.Travel_Agency_bot.util.BaseUtils;

import java.util.Map;
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
            String chatId = message.getChatId().toString();

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
                    case INTEREST -> {
                        switch (text) {
                            case "Bus" -> {
                                Map<String, String> countries = BaseUtils.countries;
                                String country = countries.get(chatId);
                                System.out.println("countries = " + countries);
                                System.out.println("country = " + country);
                            }
                            case "Train" -> {

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

                execute(new SendMessage(chatId, "Successfully registered!"));
            }

            switch (userState) {
                case START -> execute(botService.register(chatId));
                case REGISTERED, MENU -> execute(botService.menu(chatId));
                case EUROPE -> {
                    execute(botService.countryMenu(chatId, 1L, userState));
                    userService.updateState(chatId, UserState.MENU);
                }
                case ASIA -> {
                    execute(botService.countryMenu(chatId, 2L, userState));
                    userService.updateState(chatId, UserState.MENU);
                }
                case AFRICA -> {
                    execute(botService.countryMenu(chatId, 3L, userState));
                    userService.updateState(chatId, UserState.MENU);
                }
                case AMERICA -> {
                    execute(botService.countryMenu(chatId, 4L, userState));
                    userService.updateState(chatId, UserState.MENU);
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            String data = callbackQuery.getData();
            String chatId = message.getChatId().toString();

            Optional<User> currentUser = userService.getUserByChatId(chatId);
            if (currentUser.isPresent()) {
                UserState userState = currentUser.get().getState();
                switch (userState) {
                    case MENU -> {
                        execute(botService.country(chatId, data));
                        userService.updateState(chatId, UserState.INTEREST);
                    }
                    case INTEREST -> {
                        System.out.println(userState);
                        System.out.println(data);
                        switch (data) {
                            case "Buy ticket" -> execute(botService.transportMenu(chatId));

                            case "MENU" -> {
                                userService.updateState(chatId, UserState.MENU);
                                execute(new SendMessage(chatId, "Back to menu"));
                            }
                        }
                    }
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
