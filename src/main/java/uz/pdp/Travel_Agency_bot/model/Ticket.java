package uz.pdp.Travel_Agency_bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    private UUID id;
    private String user_id;
    private String name;
    private String transport;
    private String from;
    private String to;
    private String date;
    private Double price;
}
