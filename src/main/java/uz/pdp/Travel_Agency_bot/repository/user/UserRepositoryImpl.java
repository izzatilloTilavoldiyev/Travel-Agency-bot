package uz.pdp.Travel_Agency_bot.repository.user;

import com.fasterxml.jackson.core.type.TypeReference;
import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static uz.pdp.Travel_Agency_bot.util.BeanUtil.objectMapper;

public class UserRepositoryImpl implements UserRepository{

    private final static UserRepositoryImpl instance = new UserRepositoryImpl();
    private UserRepositoryImpl() {

    }

    public static UserRepositoryImpl getInstance() {
        return instance;
    }

    String path =
            "C:\\Java_OOP_Projects\\Travel_Agency_bot\\src\\main\\resources\\Users.json";

    @Override
    public ArrayList<User> readFromFile() {
        try {
            return objectMapper.readValue(new File(path), new TypeReference<ArrayList<User>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void writeToFile(ArrayList<User> users) {
        try {
            objectMapper.writeValue(new File(path), users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUserByChatId(Long chatId) {
        for (User user : readFromFile())
            if (Objects.equals(user.getChatId(), chatId)) {
                return Optional.of(user);
            }
        return Optional.empty();
    }

    @Override
    public void updateState(Long chatId, UserState userState) {
        ArrayList<User> users = readFromFile();
        for (User user : users) {
            if (Objects.equals(user.getChatId(), chatId)) {
                user.setState(userState);
                break;
            }
        }
        writeToFile(users);
    }
}
