package uz.pdp.Travel_Agency_bot.repository.ticket;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.service.bot.InlineButtons;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
        String query = "insert into tickets(id, transport, from_country, to_country, go_date, price) " +
                "values(?, ?, ?, ?, ?, ?);";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ticket.getId().toString());
            preparedStatement.setString(2, ticket.getTransport());
            preparedStatement.setString(3, ticket.getFrom());
            preparedStatement.setString(4, ticket.getTo());
            preparedStatement.setString(5, ticket.getDate());
            preparedStatement.setDouble(6, ticket.getPrice());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ticket getTicketByTransport(String chatId, String country, String transport) {
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from tickets where transport like ? and to_country like ?");
            preparedStatement.setString(1, transport);
            preparedStatement.setString(2, country);
            ResultSet resultSet = preparedStatement.executeQuery();

            Ticket ticket = null;
            if (resultSet.next()) {
                ticket = setTicketValues(resultSet);
            }
            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ticket setTicketValues(ResultSet resultSet){
        try {
            UUID id = UUID.fromString(resultSet.getString(1));
            String trans = resultSet.getString(2);
            String from = resultSet.getString(3);
            String to = resultSet.getString(4);
            String date = resultSet.getString(5);
            Double price = resultSet.getDouble(6);
            return new Ticket(id, trans, from, to, date, price);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ticket getById(String id) {
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from tickets where id = '%s'", id));
            Ticket ticket = null;
            while (resultSet.next()){
                ticket = setTicketValues(resultSet);
            }
            statement.close();
            connection.close();
            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getTicketsByUserId(String userId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                         select ticket_id from user_ticket where user_id = ?
                         """
            );
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Ticket byId = getById(resultSet.getString(1));
                tickets.add(byId);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    @Override
    public void buyTicket(String userId, String ticketId, Card card) {
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into user_ticket(user_id, ticket_id) values(?, ?)"
            );
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, ticketId);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
