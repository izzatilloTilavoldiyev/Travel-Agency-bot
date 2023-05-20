package uz.pdp.Travel_Agency_bot.util;

import uz.pdp.Travel_Agency_bot.service.bot.BotService;
import uz.pdp.Travel_Agency_bot.service.bot.BotServiceImpl;
import uz.pdp.Travel_Agency_bot.service.card.CardService;
import uz.pdp.Travel_Agency_bot.service.card.CardServiceImpl;
import uz.pdp.Travel_Agency_bot.service.ticket.TicketService;
import uz.pdp.Travel_Agency_bot.service.ticket.TicketServiceImpl;
import uz.pdp.Travel_Agency_bot.service.user.UserService;
import uz.pdp.Travel_Agency_bot.service.user.UserServiceImpl;

public interface BeanUtil {
    UserService userService = new UserServiceImpl();
    BotService botService = new BotServiceImpl();
    TicketService ticketService = new TicketServiceImpl();
    CardService cardService = new CardServiceImpl();
}
