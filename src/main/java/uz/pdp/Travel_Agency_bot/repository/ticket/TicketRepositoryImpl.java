package uz.pdp.Travel_Agency_bot.repository.ticket;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.service.bot.InlineButtons;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import static uz.pdp.Travel_Agency_bot.util.DatabaseUtils.*;

public class TicketRepositoryImpl implements TicketRepository {
    private final static TicketRepositoryImpl instance = new TicketRepositoryImpl();

    private TicketRepositoryImpl() {

    }

    public static TicketRepositoryImpl getInstance() {
        return instance;
    }
    InlineButtons inlineButtons = new InlineButtons();

    @Override
    public ArrayList<Ticket> readFromFile() {
        return new ArrayList<>();
    }

    @Override
    public void writeToFile(Ticket ticket) {
        String query = "insert into tickets(id, user_id, passenger_name," +
                " transport, from_country, to_country, go_date, price) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ticket.getId().toString());
            preparedStatement.setString(2, ticket.getUser_id());
            preparedStatement.setString(3, ticket.getPassenger_name());
            preparedStatement.setString(4, ticket.getTransport());
            preparedStatement.setString(5, ticket.getFrom());
            preparedStatement.setString(6, ticket.getTo());
            preparedStatement.setString(7, ticket.getDate());
            preparedStatement.setDouble(8, ticket.getPrice());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SendMessage getTicketByTransport(String chatId, String country, String transport) {
        ArrayList<Ticket> ticketsDB = new ArrayList<>();
        String query = "select * from tickets where transport like '"+transport+"' and to_country like '"+country+"'";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString(1));
                String user_id = resultSet.getString(2);
                String passenger_name = resultSet.getString(3);
                String trans = resultSet.getString(4);
                String from = resultSet.getString(5);
                String to = resultSet.getString(6);
                String date = resultSet.getString(7);
                Double price = resultSet.getDouble(8);
                Ticket ticket = new Ticket(id, user_id, passenger_name, trans, from, to, date, price);
                ticketsDB.add(ticket);
            }

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            StringBuilder tickets = new StringBuilder();
            if (ticketsDB.size() > 0) {
                for (Ticket ticket : ticketsDB) {
                    tickets.append("********** TICKET **********").append('\n');
                    tickets.append("\uD83D\uDE8DTransport -> ").append(ticket.getTransport()).append('\n');
                    tickets.append("\uD83C\uDDFA\uD83C\uDDFFFrom -> ").append(ticket.getFrom()).append('\n');
                    tickets.append("\uD83D\uDEA9To -> ").append(ticket.getTo()).append('\n');
                    tickets.append("\uD83D\uDCC5Date -> ").append(ticket.getDate()).append('\n');
                    tickets.append("\uD83D\uDCB4Price -> ").append(ticket.getPrice()).append('$').append('\n');
                    tickets.append('\n');
                }
                sendMessage.setText(tickets.toString());
                sendMessage.setReplyMarkup(inlineButtons.buyTicketButtons());
            }else {
                sendMessage.setText("There is no any tickets yet!");
            }
            return sendMessage;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
