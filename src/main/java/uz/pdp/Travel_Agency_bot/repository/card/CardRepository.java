package uz.pdp.Travel_Agency_bot.repository.card;

import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.repository.BaseRepository;

public interface CardRepository extends BaseRepository<Card> {
    Card userCard(String userId);
    Card getCardById(String cardId);
    Integer fillBalance(String chatId, String card, double amount);
    void ticketTransaction(Card card, Double amount);

}
