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
public class Card {
    private UUID card_id;
    private String  user_id;
    private String username;
    private String brand;
    private String card_number;
    private Double balance;
}
