package uz.pdp.Travel_Agency_bot.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;
import uz.pdp.Travel_Agency_bot.service.ticket.TicketService;
import uz.pdp.Travel_Agency_bot.service.ticket.TicketServiceImpl;
import uz.pdp.Travel_Agency_bot.util.BaseUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static uz.pdp.Travel_Agency_bot.util.BeanUtil.*;

public class Travel_bot extends TelegramLongPollingBot {
    static ExecutorService executorService = Executors.newCachedThreadPool();
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
//        Ticket ticket = new Ticket(UUID.randomUUID(), null,
//                "TICKET", "Plane", "UZB", "ITALY", "20.05.2023 08:00");
//        ticketService.add(ticket);

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
                        UserState currentState = userState;
                        switch (text) {
                            case "EUROPE" -> currentState = UserState.EUROPE;
                            case "ASIA" -> currentState = UserState.ASIA;
                            case "AFRICA" -> currentState = UserState.AFRICA;
                            case "AMERICA" -> currentState = UserState.AMERICA;
                        }
                        userService.updateState(chatId, currentState);
                        userState = currentState;
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
                case START -> {
                    executorService.execute(() -> {
                        try {
                            execute(botService.register(chatId));
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    });

                }
                case REGISTERED, MENU -> {
                    executorService.execute(() -> {
                        try {
                            execute(botService.menu(chatId));
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                case EUROPE, ASIA, AFRICA, AMERICA -> {
                    UserState finalUserState = userState;
                    long continent_id = botService.getContinentId(userState.toString());
                    executorService.execute(() -> {
                        try {
                            execute(botService.countryMenu(chatId, continent_id, finalUserState));
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        userService.updateState(chatId, UserState.MENU);
                    });
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
                        executorService.execute(() -> {
                            try {
                                execute(botService.countryServiceButtons(chatId, data));
                                execute(botService.replyKeyboardRemove(chatId));
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                            userService.updateState(chatId, UserState.COUNTRY);
                        });
                    }
                    case COUNTRY -> {
                        switch (data) {
                            case "Buy ticket" -> {
                                executorService.execute(() -> {
                                    try {
                                        execute(botService.transportMenu(chatId));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                    userService.updateState(chatId, UserState.TRANSPORT);
                                });
                                userState = UserState.TRANSPORT;
                            }
                            case "More info" -> {
                                executorService.execute(() -> {
                                    Map<String, String> countries = BaseUtils.countries;
                                    try {
                                        execute(new SendMessage(chatId, "You can click to this link to get more info"));
                                        execute(botService.countryInfo(chatId, countries.get(chatId)));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                            }
                            case "MENU" -> {
                                executorService.execute(() -> {
                                    userService.updateState(chatId, UserState.MENU);
                                });
                                userState = UserState.MENU;
                            }
                        }
                        if (userState.equals(UserState.MENU)) {
                            executorService.execute(() -> {
                                try {
                                    execute(botService.menu(chatId));
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                        }
                    }
                    case TRANSPORT -> {
                        switch (data) {
                            case "Bus", "Train", "Plane" -> {
                                Map<String, String> countries = BaseUtils.countries;
                                executorService.execute(() -> {
                                    try {
                                        execute(ticketService.getTicketByTransport(chatId, countries.get(chatId), data));
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                userService.updateState(chatId, UserState.MENU);
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
