package Tests;

import GameInterface.GameConstants;
import Main.Player;
import UNOCardTypes.NumberCard;
import UNOCardTypes.UNOCard;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest implements GameConstants {

    /**
     * Test if we can set and get the player's name as we wish.
     */
    @Test
    void TestSetAndGetPlayerName() {
        Player p1 = new Player("p1");
        assertEquals(p1.getPlayerName(), "p1");
    }

    /**
     * The player initially does not have a turn to play. By
     * switching the turn the player should now be able to play.
     */
    @Test
    void TestSwitchTurnAndIsTurnToPlay() {
        Player p1 = new Player("p1");
        assertFalse(p1.isTurnToPlay());
        p1.switchTurn();
        assertTrue(p1.isTurnToPlay());
    }

    /**
     * This test adds a yellow 0 or any arbitrary card to the player's
     * hand. Player now should have this card in hand.
     */
    @Test
    void TestHasThisCard() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        assertTrue(p1.hasThisCard(testCard));
    }

    /**
     * This test will make sure that a player initially does not have
     * any card in hand. After adding one card, the player should now
     * have card in hand.
     */
    @Test
    void TestHasCardInHand() {
        Player p1 = new Player("p1");
        assertFalse(p1.hasCardInHand());
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        assertTrue(p1.hasCardInHand());
    }

    /**
     * This test adds one card to the player's hand and checks the
     * condition. Then it adds two more cards. Now the player should
     * correctly holding 3 cards in hand.
     */
    @Test
    void TestGetNumCardInHand() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        assertEquals(1, p1.getNumCardInHand());
        p1.addCardToHand(testCard);
        p1.addCardToHand(testCard);
        assertEquals(3, p1.getNumCardInHand());
    }

    /**
     * This test adds two different cards to the player's hand.
     * Getting the last drawn card should correctly return
     * the second card in hand.
     */
    @Test
    void TestGetLastDrawnCard() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "1");
        p1.addCardToHand(testCard);
        assertEquals(testCard, p1.getLastDrawnCard());
        UNOCard newTestCard = new NumberCard(YELLOW, "5");
        p1.addCardToHand(testCard);
        p1.addCardToHand(newTestCard);
        assertEquals(newTestCard, p1.getLastDrawnCard());
    }

    /**
     * This test adds one card to the player's hand and makes sure
     * that he has a card in hand. Then after removing the card, the
     * player should not have any card in hand.
     */
    @Test
    void TestRemoveCardFromHand() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        assertTrue(p1.hasCardInHand());
        p1.removeCardFromHand(testCard);
        assertFalse(p1.hasCardInHand());
    }

    /**
     * This test adds three cards the the player's hand. The
     * get all cards method should return an array of the player's
     * hand and we check that the cards are in order and matches
     * the cards we added.
     */
    @Test
    void TestGetAllCards() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        UNOCard newTestCard = new NumberCard(YELLOW, "5");
        p1.addCardToHand(testCard);
        p1.addCardToHand(newTestCard);
        LinkedList allCards = p1.getAllCards();
        assertEquals(testCard, allCards.get(0));
        assertEquals(testCard, allCards.get(1));
        assertEquals(newTestCard, allCards.get(2));
    }
}