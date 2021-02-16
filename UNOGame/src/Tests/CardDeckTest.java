package Tests;

import Main.CardDeck;
import UNOCardTypes.NumberCard;
import UNOCardTypes.UNOCard;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Stack;

import static GameInterface.GameConstants.UNO_COLORS;
import static GameInterface.GameConstants.UNO_NUMBERS;
import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {
    private CardDeck originalCardDeck = new CardDeck();

    /**
     * This test create a brand new card deck as well as a card stack.
     * The size of the stack should be 108, containing all cards before
     * any card is dealt to the player and before adding the first card
     * into the discard pile to start the game.
     */
    @Test
    void TestGetCardDeck() {
        assertEquals(108, originalCardDeck.getCardDeck().size());
        originalCardDeck.addWildCards();
        assertEquals(116, originalCardDeck.getCardDeck().size());
        originalCardDeck.addActionCards();
        assertEquals(140, originalCardDeck.getCardDeck().size());
        originalCardDeck.addNumberCards();
        assertEquals(216, originalCardDeck.getCardDeck().size());
    }

    /**
     * This test shuffle the cards from the discard pile to create a new
     * card deck as well as the card stack. The size of this stack was
     * intentionally 1 less than the cards in the discard pile because
     * one card should remain in the discard pile as "current card" so
     * that the players would know which card to follow.
     */
    @Test
    void TestGetCardDeckFromDiscardPile() {
        Stack <UNOCard> discardPile = new Stack<>();
        discardPile.addAll(originalCardDeck.getCardDeck());
        CardDeck newOne = new CardDeck(discardPile);
        assertEquals(107, newOne.getCardDeck().size());
    }
}
