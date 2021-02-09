package Tests;

import GameInterface.GameConstants;
import Main.Player;
import UNOCardTypes.NumberCard;
import UNOCardTypes.UNOCard;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest implements GameConstants {

    @Test
    void TestSetAndGetPlayerName() {
        Player p1 = new Player("p1");
        assertEquals(p1.getPlayerName(), "p1");
    }

    @Test
    void TestSwitchTurnAndIsTurnToPlay() {
        Player p1 = new Player("p1");
        p1.switchTurn();
        assertTrue(p1.isTurnToPlay());
    }

    @Test
    void TestHasThisCard() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        assertTrue(p1.hasThisCard(testCard));
    }

    @Test
    void TestHasCardInHand() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        assertTrue(p1.hasCardInHand());
    }

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

    @Test
    void TestGetLastDrawnCard() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        assertEquals(testCard, p1.getLastDrawnCard());
        UNOCard newTestCard = new NumberCard(YELLOW, "5");
        p1.addCardToHand(testCard);
        p1.addCardToHand(newTestCard);
        assertEquals(newTestCard, p1.getLastDrawnCard());
    }

    @Test
    void TestRemoveCardFromHand() {
        Player p1 = new Player("p1");
        UNOCard testCard = new NumberCard(YELLOW, "0");
        p1.addCardToHand(testCard);
        p1.removeCardFromHand(testCard);
        assertFalse(p1.hasCardInHand());
    }

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