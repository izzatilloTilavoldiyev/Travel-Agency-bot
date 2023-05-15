package uz.pdp.Travel_Agency_bot.repository.user;

import uz.pdp.Travel_Agency_bot.model.User;
import uz.pdp.Travel_Agency_bot.model.UserState;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

import static uz.pdp.Travel_Agency_bot.util.DatabaseUtils.*;

public class UserRepositoryImpl implements UserRepository {

    private final static UserRepositoryImpl instance = new UserRepositoryImpl();

    private UserRepositoryImpl() {

    }

    public static UserRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public ArrayList<User> readFromFile() {
        ArrayList<User> users = new ArrayList<>();
        String query = "select * from users";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = (User) resultSet;
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void writeToFile(User user) {
        String query = "insert into users(chat_id, first_name, last_name, phone_number, state)" +
                " values(?,?,?,?,?) ;";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getChatId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getState().toString());

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUserByChatId(String chatId) {
        String query = "select * from users where chat_id like '" + chatId + "';";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                String first_name = resultSet.getString(2);
                String last_name = resultSet.getString(3);
                String phone_number = resultSet.getString(4);
                UserState state = UserState.valueOf(resultSet.getString(5));

                User user = new User(id, first_name, last_name, phone_number, state);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateState(String chatId, UserState userState) {
        String query = "UPDATE users SET state = '" + userState + "'" +
                " WHERE chat_id = '" + chatId + "'";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}