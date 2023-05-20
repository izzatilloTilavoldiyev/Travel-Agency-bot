package uz.pdp.Travel_Agency_bot.service.card;

import uz.pdp.Travel_Agency_bot.model.Card;
import uz.pdp.Travel_Agency_bot.repository.card.CardRepository;
import uz.pdp.Travel_Agency_bot.repository.card.CardRepositoryImpl;

public class CardServiceImpl implements CardService{
    static final CardRepository cardRepository = CardRepositoryImpl.getInstance();
    @Override
    public void add(Card card) {
        cardRepository.writeToFile(card);
    }

    @Override
    public Card userCard(String userId) {
        return cardRepository.userCard(userId);
    }

    @Override
    public Integer fillBalance(String chatId, String card, double amount) {
        return cardRepository.fillBalance(chatId, card, amount);
    }
}
