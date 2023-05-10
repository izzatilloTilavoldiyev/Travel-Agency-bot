package uz.pdp.Travel_Agency_bot.repository.user;

import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;
import uz.pdp.Travel_Agency_bot.repository.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> getUserByChatId(Long chatId);
    void updateState(Long chatId, UserState userState);
}
