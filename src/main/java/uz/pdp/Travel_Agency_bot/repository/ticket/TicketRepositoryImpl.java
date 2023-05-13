package uz.pdp.Travel_Agency_bot.repository.ticket;

import uz.pdp.Travel_Agency_bot.model.Ticket;

import java.util.ArrayList;

public class TicketRepositoryImpl implements TicketRepository {
    private final static TicketRepositoryImpl instance = new TicketRepositoryImpl();

    private TicketRepositoryImpl() {

    }

    public static TicketRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public ArrayList<Ticket> readFromFile() {
        return new ArrayList<>();
    }

    @Override
    public void writeToFile(Ticket ticket) {

    }

}
