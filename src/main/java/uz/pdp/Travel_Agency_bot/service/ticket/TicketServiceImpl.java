package uz.pdp.Travel_Agency_bot.service.ticket;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.Travel_Agency_bot.model.Ticket;
import uz.pdp.Travel_Agency_bot.repository.ticket.TicketRepository;
import uz.pdp.Travel_Agency_bot.repository.ticket.TicketRepositoryImpl;

public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository = TicketRepositoryImpl.getInstance();

    @Override
    public void add(Ticket ticket) {
        ticketRepository.writeToFile(ticket);
    }

    @Override
    public SendMessage getTicketByTransport(String chatId, String country, String transport) {
        return ticketRepository.getTicketByTransport(chatId, country, transport);
    }
}
