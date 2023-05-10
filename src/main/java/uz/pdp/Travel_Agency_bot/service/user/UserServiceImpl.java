package uz.pdp.Travel_Agency_bot.service.user;

import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;
import uz.pdp.Travel_Agency_bot.repository.user.UserRepository;
import uz.pdp.Travel_Agency_bot.repository.user.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.Optional;

public class UserServiceImpl implements UserService{

    UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public void add(User user) {
        ArrayList<User> users = userRepository.readFromFile();
        users.add(user);
        userRepository.writeToFile(users);
    }

    @Override
    public Optional<User> getUserByChatId(Long chatId) {
        return userRepository.getUserByChatId(chatId);
    }

    @Override
    public void updateState(Long chatId, UserState userState) {
        userRepository.updateState(chatId, userState);
    }

}
