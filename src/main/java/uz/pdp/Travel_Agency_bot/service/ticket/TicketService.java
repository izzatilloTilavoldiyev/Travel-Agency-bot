package uz.pdp.Travel_Agency_bot.service.ticket;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.service.BaseService;

public interface TicketService extends BaseService<Ticket> {
    SendMessage getTicketByTransport(String chatId, String country, String transport);
}
