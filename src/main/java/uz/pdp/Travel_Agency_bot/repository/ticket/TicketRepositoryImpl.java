package uz.pdp.Travel_Agency_bot.repository.ticket;

import com.fasterxml.jackson.core.type.TypeReference;
import uz.pdp.Travel_Agency_bot.model.Ticket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static uz.pdp.Travel_Agency_bot.util.BeanUtil.objectMapper;

public class TicketRepositoryImpl implements TicketRepository{
    private final static TicketRepositoryImpl instance = new TicketRepositoryImpl();
    private TicketRepositoryImpl() {

    }

    public static TicketRepositoryImpl getInstance() {
        return instance;
    }

    String path = "C:\\Java_OOP_Projects\\Travel_Agency_bot\\src\\main\\resources\\Ticket.json";
    @Override
    public ArrayList<Ticket> readFromFile() {
        try {
            return objectMapper.readValue(new File(path), new TypeReference<ArrayList<Ticket>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile(ArrayList<Ticket> tickets) {
        try {
            objectMapper.writeValue(new File(path), tickets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
