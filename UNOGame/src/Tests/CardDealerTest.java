package Tests;

import Main.CardDealer;
import Main.CardDeck;
import Main.Player;
import UNOCardTypes.UNOCard;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class CardDealerTest {
    private CardDealer cardDealer = new CardDealer();

    /**
     * This test create a card dealer using the default constructor
     * and deal cards to both players.
     */
    @Test
    void TestDealCardToPlayers() {
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Player[] player = new Player[]{p1, p2};
        cardDealer.createCardStackFromCardDeck();
        cardDealer.dealCardToPlayers(player);
        assertEquals(7, p1.getNumCardInHand());
        assertEquals(7, p2.getNumCardInHand());
    }

    /**
     * This test create a card dealer from a discard pile and try
     * to get a card from the new card dealer.
     */
    @Test
    void TestGetNewCardFromCardDeck() {
        Stack<UNOCard> discardPile = new Stack<>();
        CardDeck cardDeck = new CardDeck();
        discardPile.addAll(cardDeck.getCardDeck());
        CardDealer cardDealerFromDiscardPile = new CardDealer(discardPile);
        cardDealerFromDiscardPile.createCardStackFromCardDeck();
        assertNotNull(cardDealerFromDiscardPile.getNewCardFromCardDeck());
    }
}