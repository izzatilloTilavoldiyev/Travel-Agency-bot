package uz.pdp.Travel_Agency_bot.repository.ticket;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.repository.BaseRepository;

import java.util.ArrayList;

public interface TicketRepository extends BaseRepository<Ticket> {
    SendMessage getTicketByTransport(String chatId, String country, String transport);
}
