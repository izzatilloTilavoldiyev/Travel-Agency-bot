package uz.pdp.Travel_Agency_bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private String chatId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserState state;
}
