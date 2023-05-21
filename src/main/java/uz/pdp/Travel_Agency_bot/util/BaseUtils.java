package uz.pdp.Travel_Agency_bot.util;

import uz.pdp.Travel_Agency_bot.model.Ticket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseUtils {
    public static Map<String, String> countries = new ConcurrentHashMap<>();
    public static Map<String, Ticket> ticketMap = new ConcurrentHashMap<>();

}
