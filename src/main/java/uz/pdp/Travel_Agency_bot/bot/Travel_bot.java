package uz.pdp.Travel_Agency_bot.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;
import uz.pdp.Travel_Agency_bot.util.BaseUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static uz.pdp.Travel_Agency_bot.util.BeanUtil.*;
import static uz.pdp.Travel_Agency_bot.util.Constants.*;

public class Travel_bot extends TelegramLongPollingBot {
    static ExecutorService executorService = Executors.newCachedThreadPool();
    static Random random = new Random();
    static String cardNumber = "";
    static double amount = 0.0;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        executorService.execute(() -> {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                String text = message.getText();
                String chatId = message.getChatId().toString();

                Optional<User> currentUser = userService.getUserByChatId(chatId);
                UserState userState = UserState.START;

                if (currentUser.isPresent()) {
                    userState = currentUser.get().getState();
                    switch (userState) {
                        case REGISTERED -> {
                            userService.updateState(chatId, UserState.BASE_MENU);
                            userState = UserState.BASE_MENU;
                        }
                        case BASE_MENU -> {
                            switch (text) {
                                case Travel -> {
                                    userService.updateState(chatId, UserState.MENU);
                                    userState = UserState.MENU;
                                }
                                case My_account -> {
                                    userService.updateState(chatId, UserState.MyAccount);
                                    userState = UserState.MyAccount;
                                }
                            }
                        }
                        case MENU -> {
                            UserState currentState = userState;
                            switch (text) {
                                case EUROPE -> currentState = UserState.EUROPE;
                                case ASIA -> currentState = UserState.ASIA;
                                case AFRICA -> currentState = UserState.AFRICA;
                                case AMERICA -> currentState = UserState.AMERICA;
                                case BASE_MENU -> {
                                    userService.updateState(chatId, UserState.BASE_MENU);
                                    currentState = UserState.BASE_MENU;
                                }
                            }
                            userState = currentState;
                        }
                        case MyAccount -> {
                            Card card = cardService.userCard(chatId);
                            switch (text) {
                                case BASE_MENU -> {
                                    userService.updateState(chatId, UserState.BASE_MENU);
                                    userState = UserState.BASE_MENU;
                                }
                                case My_cards -> {
                                    try {
                                        execute(botService.cardMenuButtons(card, chatId));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                case My_tickets -> {
                                    try {
                                        execute(botService.display_1_button(chatId,
                                                "Your tickets", Delete_tickets));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                case Balance -> {
                                    StringBuilder description = new StringBuilder();
                                    description.append("Your balance:").append('\n');
                                    description.append(card.getBalance()).append(" $").append('\n').append('\n');
                                    description.append("Your cards: ").append('\n');
                                    description.append("\uD83D\uDCB3 ").append(card.getCard_number())
                                            .append("  ➡").append(card.getBrand()).append(" ⭐").append('\n');
                                    description.append("\t\t").append(card.getBalance()).append(" $");

                                    try {
                                        execute(botService.display_1_button(chatId,
                                                description.toString(), Fill_balance));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                case History -> {
                                    try {
                                        execute(botService.display_1_button(chatId,
                                                "Your histories", Clean_histories));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                        case FullName -> {
                            Card card = generateCard(chatId, text);
                            cardService.add(card);
                            try {
                                execute(new SendMessage(chatId, "Successfully created!"));
                                userService.updateState(chatId, UserState.MyAccount);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case FillBalance -> {
                            try {
                                cardNumber = text;
                                execute(new SendMessage(chatId, "Enter amount:"));
                                userService.updateState(chatId, UserState.EnterAmount);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case EnterAmount -> {
                            try {
                                amount = Double.parseDouble(text);
                            }catch (Exception e) {
                                try {
                                    execute(new SendMessage(chatId, "Wrong amount"));
                                } catch (TelegramApiException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                            Integer res = cardService.fillBalance(chatId, cardNumber, amount);
                            if (res == 200) {
                                try {
                                    execute(new SendMessage(chatId, "Successfully felt"));
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                            }else {
                                try {
                                    execute(new SendMessage(chatId, "Something went wrong!"));
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            userService.updateState(chatId, UserState.MyAccount);
                            userState = UserState.MyAccount;
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
                    try {
                        execute(new SendMessage(chatId, "Successfully registered!"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }

                switch (userState) {
                    case START -> {
                        try {
                            execute(botService.register(chatId));
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case REGISTERED, BASE_MENU -> {
                        try {
                            execute(botService.baseMenuButton(chatId));
                            userService.updateState(chatId, UserState.BASE_MENU);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case MENU -> {
                        try {
                            execute(botService.menu(chatId));
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case EUROPE, ASIA, AFRICA, AMERICA -> {
                        long continent_id = botService.getContinentId(userState.toString());
                        try {
                            execute(botService.countryMenu(chatId, continent_id, userState));
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        userService.updateState(chatId, UserState.MENU);
                    }
                    case MyAccount -> {
                        try {
                            execute(botService.userAccountButtons(chatId));
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (Objects.equals(userState, UserState.MENU)) {
                    try {
                        execute(botService.menu(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
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
                            try {
                                execute(botService.countryServiceButtons(chatId, data));
                                execute(botService.replyKeyboardRemove(chatId));
                                userService.updateState(chatId, UserState.COUNTRY);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case COUNTRY -> {
                            switch (data) {
                                case Buy_ticket -> {
                                    try {
                                        execute(botService.transportMenu(chatId));
                                        userService.updateState(chatId, UserState.TRANSPORT);
                                        userState = UserState.TRANSPORT;
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                case More_info -> {
                                    Map<String, String> countries = BaseUtils.countries;
                                    try {
                                        execute(new SendMessage(chatId, "You can click to this link to get more info"));
                                        execute(botService.countryInfo(chatId, countries.get(chatId)));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                case MENU -> {
                                    userService.updateState(chatId, UserState.MENU);
                                    userState = UserState.MENU;
                                }
                            }
                        }
                        case TRANSPORT -> {
                            switch (data) {
                                case Bus, Train, Plane -> {
                                    Map<String, String> countries = BaseUtils.countries;
                                    try {
                                        execute(ticketService.getTicketByTransport(chatId, countries.get(chatId), data));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                case "1" -> {
                                    try {
                                        execute(new SendMessage(chatId, "Bought"));
                                        userService.updateState(chatId, UserState.MENU);
                                        userState = UserState.MENU;
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                case "0" -> {
                                    userService.updateState(chatId, UserState.MENU);
                                    userState = UserState.MENU;
                                }
                            }
                        }
                        case MyAccount -> {
                            switch (data) {
                                case Create_card -> {
                                    SendMessage sendMessage = new SendMessage(chatId, "Enter your full name");
                                    try {
                                        execute(sendMessage);
                                        userService.updateState(chatId, UserState.FullName);
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                case Fill_balance -> {
                                    SendMessage sendMessage = new SendMessage(chatId, "Enter card number:");
                                    try {
                                        execute(sendMessage);
                                        userService.updateState(chatId, UserState.FillBalance);
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                            }
                        }
                    }
                    if (Objects.equals(userState, UserState.MENU)) {
                        try {
                            execute(botService.menu(chatId));
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    @Override
    public String getBotUsername() {
        return "G23_Travel_bot";
    }

    @Override
    public String getBotToken() {
        return "6283576225:AAHlNdPkFmrGR5wXD5QgQt4-MvTGJQVqHYQ";
    }

    public Card generateCard(String user_id, String username) {
        long card_number = random.nextLong(100_000, 1_000_000);
        return new Card(UUID.randomUUID(), user_id, username, Travel_card, Long.toString(card_number), 0D);
    }
}
