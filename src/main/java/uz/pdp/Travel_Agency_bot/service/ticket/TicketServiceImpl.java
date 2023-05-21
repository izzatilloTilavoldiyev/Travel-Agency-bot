package uz.pdp.Travel_Agency_bot.service.ticket;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.repository.card.CardRepository;
import uz.pdp.Travel_Agency_bot.repository.card.CardRepositoryImpl;
import uz.pdp.Travel_Agency_bot.repository.ticket.TicketRepository;
import uz.pdp.Travel_Agency_bot.repository.ticket.TicketRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository = TicketRepositoryImpl.getInstance();
    CardRepository cardRepository = CardRepositoryImpl.getInstance();

    @Override
    public void add(Ticket ticket) {
        ticketRepository.writeToFile(ticket);
    }

    @Override
    public Ticket getTicketByTransport(String chatId, String country, String transport) {
        return ticketRepository.getTicketByTransport(chatId, country, transport);
    }

    @Override
    public Integer buyTicket(String userId, Ticket ticket, Card card) {
        if (card.getBalance() >= ticket.getPrice()) {
            ticketRepository.buyTicket(userId, ticket.getId().toString(), card);
            cardRepository.ticketTransaction(card, ticket.getPrice());
            return 200;
        }else {
            return 400;
        }
    }

    @Override
    public List<Ticket> getTicketsByUserId(String userId) {
        return ticketRepository.getTicketsByUserId(userId);
    }
}
