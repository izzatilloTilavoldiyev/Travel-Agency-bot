package uz.pdp.Travel_Agency_bot.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uz.pdp.Travel_Agency_bot.service.bot.BotService;
import uz.pdp.Travel_Agency_bot.service.bot.BotServiceImpl;
import uz.pdp.Travel_Agency_bot.service.user.UserService;
import uz.pdp.Travel_Agency_bot.service.user.UserServiceImpl;

public interface BeanUtil {
    ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    UserService userService = new UserServiceImpl();
    BotService botService = new BotServiceImpl();
}
