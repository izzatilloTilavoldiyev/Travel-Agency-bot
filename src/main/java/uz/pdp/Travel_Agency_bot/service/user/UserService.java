package uz.pdp.Travel_Agency_bot.service.user;

import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;
import uz.pdp.Travel_Agency_bot.service.BaseService;

import java.util.Optional;

public interface UserService extends BaseService<User> {

    Optional<User> getUserByChatId(Long chatId);
    void updateState(Long chatId, UserState userState);
}
