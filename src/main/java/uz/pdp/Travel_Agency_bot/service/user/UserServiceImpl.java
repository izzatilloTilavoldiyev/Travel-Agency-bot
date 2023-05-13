package uz.pdp.Travel_Agency_bot.service.user;

import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;
import uz.pdp.Travel_Agency_bot.repository.user.UserRepository;
import uz.pdp.Travel_Agency_bot.repository.user.UserRepositoryImpl;

import java.util.Optional;

public class UserServiceImpl implements UserService{

    UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public void add(User user) {
        userRepository.writeToFile(user);
    }

    @Override
    public Optional<User> getUserByChatId(String chatId) {
        return userRepository.getUserByChatId(chatId);
    }

    @Override
    public void updateState(String chatId, UserState userState) {
        userRepository.updateState(chatId, userState);
    }

}
