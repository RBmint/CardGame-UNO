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
    /**
     * This test mocks the terminal input and try set no player initially,
     * which should not be accepted, then try to set only 1 player.
     * The game should start with 2 players automatically.
     */
    @Test
    void TestGetLessThanTwoActivePlayers() {
        String data = "nothing\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertEquals(2, game.getActivePlayers().length);
    }

    /**
     * This test mocks the terminal input try to set 10 players.
     * The game is not allowed to have more than 9 players and
     * should start with 9 players automatically.
     */
    @Test
    void TestGetMoreThanTenActivePlayers() {
        String data = "10";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertEquals(9, game.getActivePlayers().length);
    }

    /**
     * The game will start with 5, or any legal arbitrary number
     * of players, and the current player will be given a card and
     * thus have 8 cards in hand. Then the same card will be removed
     * so that he will be back to 7 cards in hand once more.
     */
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

    /**
     * The game will start with only 2 players. The first player
     * initially have 7 cards in hand, and after drawing one card
     * from the card deck, his turn will be skipped. So it will be
     * player2's turn and we check his next player, which is player1
     * who just drawn a card, and he will have 8 cards in hand.
     */
    @Test
    void playerDrawOneCard() {
        String data = "2";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        UNOCard currentCard = new WildCard(WILD_CARD);
        assertEquals(7, game.getCurrentPlayingPlayer().getNumCardInHand());
        game.playerDrawOneCard(currentCard);
        assertEquals(8, game.getNextPlayer().getNumCardInHand());
    }

    /**
     * This test will use a yellow 0 as the current card, and check
     * if the following cards can be played immediately. Any yellow
     * action card could; a green 0 could; any wild card could; a
     * green 1 would not be played immediately.
     */
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

    /**
     * The game starts with 5 players and the current active player will
     * be index 0 in active player array. Then we skip current player's
     * turn and check that now the current player is index 1 in the array.
     */
    @Test
    void skipCurrentPlayerTurn() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertEquals(game.getActivePlayers()[0], game.getCurrentPlayingPlayer());
        game.skipCurrentPlayerTurn();
        assertEquals(game.getActivePlayers()[1], game.getCurrentPlayingPlayer());
    }

    /**
     * The game starts with 5 players and we check if the current active
     * player is the first player in the active player array, which is
     * by name player 1.
     */
    @Test
    void getCurrentPlayingPlayer() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        Player[] p = game.getActivePlayers();
        game.printCurrentPlayingPlayer();
        assertEquals(p[0], game.getCurrentPlayingPlayer());
    }

    /**
     * This test will let the first player draw 4 cards to hand.
     * The number of card that player holds should thus be 11.
     */
    @Test
    void playerDrawMultipleCards() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        game.playerDrawMultipleCards(4);
        Player p = game.getCurrentPlayingPlayer();
        assertEquals(11, p.getNumCardInHand());
    }

    /**
     * This test makes sure that the game is not over at the
     * very beginning.
     */
    @Test
    void isOver() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertFalse(game.isOver());
    }

    /**
     * The game will start with 5 players. Initially, the card deck
     * contains 108 cards. After dealing 7 cards to each player, the
     * deck will then have 73 cards left when the game begins.
     */
    @Test
    void numRemainingCardInDeck() {
        String data = "5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        assertEquals(73, game.numRemainingCardInDeck());
    }

    /**
     * This test addresses multiple scenarios for a non-number
     * starting card. Getting a wild card should choose a random
     * color to start the game; getting a draw two card will make
     * the first player to draw 2 cards and skip his turn; getting a
     * skip card will skip the first player's turn and getting a
     * reverse card should reverse the playing sequence immediately.
     */
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