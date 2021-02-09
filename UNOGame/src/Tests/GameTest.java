package Tests;

import GameInterface.GameConstants;
import GameInterface.UNOCardConstants;
import Main.Game;
import Main.Player;
import UNOCardTypes.ActionCard;
import UNOCardTypes.NumberCard;
import UNOCardTypes.UNOCard;
import UNOCardTypes.WildCard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameTest implements GameConstants {

    @Test
    void TESTGetActivePlayers() {
        String data = "nothing\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertEquals(2, game.getActivePlayers().length);
    }

    @Test
    void getActivePlayers() {
        String data = "10";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertEquals(8, game.getActivePlayers().length);
    }

    @Test
    void removePlayedCardFromPlayer() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        Player p = game.getCurrentPlayingPlayer();
        UNOCard currentCard = new NumberCard(YELLOW, "10");
        p.addCardToHand(currentCard);
        assertEquals(8, p.getNumCardInHand());
        game.removePlayedCardFromPlayer(currentCard);
        assertEquals(7, game.getCurrentPlayingPlayer().getNumCardInHand());
    }

    @Test
    void playerDrawOneCard() {
        String data = "2";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        UNOCard currentCard = new WildCard(WILD_CARD);
        game.playerDrawOneCard(currentCard);
        game.playerDrawOneCard(currentCard);
        assertEquals(8, game.getCurrentPlayingPlayer().getNumCardInHand());
    }

    @Test
    void canBePlayedImmediately() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        UNOCard currentCard = new NumberCard(YELLOW, "0");
        UNOCard drawnCard1 = new ActionCard(YELLOW, ACTION_CARD);
        UNOCard drawnCard2 = new NumberCard(GREEN, "0");
        UNOCard drawnCard3 = new WildCard(WILD_CARD);
        UNOCard drawnCard4 = new NumberCard(GREEN, "1");
        assertTrue(game.canBePlayedImmediately(currentCard, drawnCard1));
        assertTrue(game.canBePlayedImmediately(currentCard, drawnCard2));
        assertTrue(game.canBePlayedImmediately(currentCard, drawnCard3));
        assertFalse(game.canBePlayedImmediately(currentCard,drawnCard4));
    }

    @Test
    void skipCurrentPlayerTurn() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        game.skipCurrentPlayerTurn();
        assertEquals(game.getActivePlayers()[1], game.getCurrentPlayingPlayer());
    }

    @Test
    void getCurrentPlayingPlayer() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        Player[] p = game.getActivePlayers();
        game.printCurrentPlayingPlayer();
        assertEquals(p[0], game.getCurrentPlayingPlayer());
    }

    @Test
    void playerDrawMultipleCards() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        game.playerDrawMultipleCards(4);
        Player p = game.getCurrentPlayingPlayer();
        assertEquals(11, p.getNumCardInHand());
    }

    @Test
    void isOver() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertFalse(game.isOver());
    }


    @Test
    void numRemainingCardInDeck() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertEquals(73, game.numRemainingCardInDeck());
    }

    @Test
    void specialActionForNonNumberStartingCard() {
        String data = "2";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        UNOCard randomStartingCard = new WildCard(WILD_CARD);
        game.specialActionForNonNumberStartingCard(randomStartingCard);
        assertNotNull(randomStartingCard.getCardColor());

        randomStartingCard = new ActionCard(GREEN, DRAW_TWO_CARD);
        game.specialActionForNonNumberStartingCard(randomStartingCard);
        assertEquals(9, game.getNextPlayer().getNumCardInHand());

        randomStartingCard = new ActionCard(GREEN, SKIP_CARD);
        game.specialActionForNonNumberStartingCard(randomStartingCard);
        assertTrue(game.getActivePlayers()[0].isTurnToPlay());

        randomStartingCard = new ActionCard(GREEN, REVERSE_CARD);
        game.specialActionForNonNumberStartingCard(randomStartingCard);
        assertTrue(game.getActivePlayers()[0].isTurnToPlay());
    }
}