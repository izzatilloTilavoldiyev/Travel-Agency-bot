package uz.pdp.Travel_Agency_bot.repository.card;

import uz.pdp.Travel_Agency_bot.model.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import static uz.pdp.Travel_Agency_bot.util.DatabaseUtils.*;

public class CardRepositoryImpl implements CardRepository{
    private static final CardRepositoryImpl instance = new CardRepositoryImpl();
    private CardRepositoryImpl() {

    }

    public static CardRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public ArrayList<Card> readFromFile() {
        return null;
    }

    @Override
    public void writeToFile(Card card) {
        String query = "insert into cards(card_id, user_id, username, brand, card_number, balance)" +
                " values(?, ?, ?, ?, ?, ?);";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, card.getCard_id().toString());
            preparedStatement.setString(2, card.getUser_id());
            preparedStatement.setString(3, card.getUsername());
            preparedStatement.setString(4, card.getBrand());
            preparedStatement.setString(5, card.getCard_number());
            preparedStatement.setDouble(6, card.getBalance());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Card userCard(String userId) {
        String query = "select * from cards where user_id = '" + userId + "'";
        return getById(query);
    }

    @Override
    public Card getCardById(String cardId) {
        String query = "select * from cards where card_id = '" + cardId + "'";
        return getById(query);
    }

    private Card getById(String query) {
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String card_id = resultSet.getString(1);
                String user_id = resultSet.getString(2);
                String username = resultSet.getString(3);
                String brand = resultSet.getString(4);
                String card_number = resultSet.getString(5);
                double balance = resultSet.getDouble(6);
                return new Card(UUID.fromString(card_id), user_id, username, brand, card_number, balance);
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer fillBalance(String chatId, String card, double amount) {
        Card userCard = userCard(chatId);
        if (userCard != null) {
            double aMount = userCard.getBalance() + amount;
            String query = "update cards set balance = "+aMount+" where card_number = '"+card+"'";
            try {
                Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                preparedStatement.close();
                connection.close();
                return 200;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            return 400;
        }
    }
}
