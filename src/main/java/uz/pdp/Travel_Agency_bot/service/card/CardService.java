package uz.pdp.Travel_Agency_bot.service.card;

import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.service.BaseService;

public interface CardService extends BaseService<Card> {
    Card userCard(String userId);
    Card getCardById(String cardId);
    Integer fillBalance(String chatId, String card, double amount);
}
