package uz.pdp.Travel_Agency_bot.service.ticket;

import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.repository.ticket.TicketRepository;
import uz.pdp.Travel_Agency_bot.repository.ticket.TicketRepositoryImpl;

import java.util.ArrayList;

public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository = TicketRepositoryImpl.getInstance();

    @Override
    public void add(Ticket ticket) {
        ArrayList<Ticket> tickets = ticketRepository.readFromFile();
        tickets.add(ticket);
        ticketRepository.writeToFile(tickets);
    }
}