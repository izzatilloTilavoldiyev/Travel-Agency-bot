package uz.pdp.Travel_Agency_bot.repository.ticket;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.repository.BaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface TicketRepository extends BaseRepository<Ticket> {
    Ticket getTicketByTransport(String chatId, String country, String transport);

    Ticket getById(String id);

    List<Ticket> getTicketsByUserId(String userId);
    void buyTicket(String userId, String ticketId, Card card);
}
