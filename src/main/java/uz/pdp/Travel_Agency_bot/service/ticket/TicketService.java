package uz.pdp.Travel_Agency_bot.service.ticket;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.service.BaseService;

import java.util.ArrayList;
import java.util.List;

public interface TicketService extends BaseService<Ticket> {
    Ticket getTicketByTransport(String chatId, String country, String transport);
    Integer buyTicket(String userId, Ticket ticket, Card card);
    List<Ticket> getTicketsByUserId(String userId);
}
