package uz.pdp.Travel_Agency_bot.util;

import uz.pdp.Travel_Agency_bot.service.bot.BotService;
import uz.pdp.Travel_Agency_bot.service.bot.BotServiceImpl;
import uz.pdp.Travel_Agency_bot.service.user.UserService;
import uz.pdp.Travel_Agency_bot.service.user.UserServiceImpl;

public interface BeanUtil {
    UserService userService = new UserServiceImpl();
    BotService botService = new BotServiceImpl();
    String url = "jdbc:postgresql://localhost:5432/travel-agency-bot";
    String dbUser = "postgres";
    String dbPassword = "4999";
}
