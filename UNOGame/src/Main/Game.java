package Main;

import GameInterface.GameConstants;
import UNOCardTypes.UNOCard;
import UNOCardTypes.WildCard;

import java.util.*;

/**
 * This class deals with the core functionality of the UNO game.
 */
public class Game implements GameConstants {

    private boolean gameIsOver;
    private Player[] activePlayers;

    public CardDealer cardDealer;
    public Stack<UNOCard> cardStack;

    /**
     * The default constructor will take user input to create corresponding players.
     */
    public Game() {
        int playerCount = getDesiredPlayerNumber();
        int aiPlayerCount = getDesiredAIPlayerNumber(playerCount);

        activePlayers = new Player[playerCount];
        for (int i = 1; i <= aiPlayerCount - 1; i++) {
            activePlayers[i - 1] = new Player("Baseline AI Player" + i);
        }
        if (aiPlayerCount != 0) {
            activePlayers[aiPlayerCount - 1] = new Player("Strategic AI Player" + aiPlayerCount); /* One strategic AI by default*/
        }
        for (int i = aiPlayerCount + 1; i <= playerCount; i++) {
            activePlayers[i - 1] = new Player("Player" + i);
        }

        activePlayers[0].switchTurn(); /* The first player start to play by default*/

        cardDealer = new CardDealer();
        cardStack = cardDealer.createCardStackFromCardDeck();
        cardDealer.dealCardToPlayers(activePlayers);
        gameIsOver = false;
    }

    /**
     * Get the desired total number of players from user input.
     * @return the number of total players
     */
    private int getDesiredPlayerNumber() {
        int playerCount;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please indicate the number of total players:");
            String input = scanner.next();
            try {
                playerCount = Integer.parseInt(input);
                if (playerCount < 2) {
                    System.out.println("Game need more than 1 player");
                    System.out.println("Current player count is automatically set to 2");
                    playerCount = 2;
                }
                if (playerCount > 8) {
                    System.out.println("Game cannot have more than 8 players");
                    System.out.println("Current player count is automatically set to 8");
                    playerCount = 8;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number, please re-enter");
            }
        }
        return playerCount;
    }

    /**
     * Get the desired AI player count from user input. There cannot be more AI players than
     * total players, and should have at least one real player.
     * @param currentPlayerCount the total player count to be compared
     * @return the number of AI players
     */
    private int getDesiredAIPlayerNumber(int currentPlayerCount) {
        int aiCount;
        Scanner scanner = new Scanner(System.in);
        //while (true) {
            System.out.println("Please indicate the number of AI players:");
            try {
                String input = scanner.next();
                aiCount = Integer.parseInt(input);
                if (aiCount < 0) {
                    aiCount = 0;
                }
                if (aiCount > currentPlayerCount) {
                    System.out.println("Cannot have more AI players than total players");
                    //System.out.println("Game need at least one real player");
                    System.out.println("AI player count is automatically set to " + currentPlayerCount);
                    aiCount = currentPlayerCount;
                }
                //break;
            } catch (NoSuchElementException | NumberFormatException e) {
                System.out.println("Invalid input, AI player count is automatically set to 0");
                aiCount = 0;
                //break;
            }
        //}
        return aiCount;
    }

    public UNOCard drawNewCardFromCardStack() {
        return cardDealer.getNewCardFromCardDeck();
    }

    public Player[] getActivePlayers() {
        return activePlayers;
    }

    /**
     * Remove a card that is played by the player from his hand.
     * @param cardPlayed the card to be removed
     */
    public void removePlayedCardFromPlayer(UNOCard cardPlayed) {
        Player p = getCurrentPlayingPlayer();
        if (p.hasThisCard(cardPlayed)) {
            p.removeCardFromHand(cardPlayed);
        }
    }

    /**
     * Draw one card for the player and check if he can play it immediately.
     * @param currentCard the card to compare to to determine if the drawn
     *                    card can be played immediately
     */
    public void playerDrawOneCard(UNOCard currentCard) {
        Player p = getCurrentPlayingPlayer();
        UNOCard drawnCard = drawNewCardFromCardStack();
        p.addCardToHand(drawnCard);
        if (!canBePlayedImmediately(currentCard, drawnCard)) {
            System.out.println("The card you just drawn cannot be played. Your turn is over");
            skipCurrentPlayerTurn();
        }
    }

    /**
     * Check if the card drawn can be played immediately according to the rule.
     * @param currentCard the card to compare to
     * @param drawnCard the card player just drawn
     * @return true if he can play it, false otherwise
     */
    public boolean canBePlayedImmediately(UNOCard currentCard, UNOCard drawnCard) {
        if (currentCard.getCardColor().equals(drawnCard.getCardColor())) {
            return true;
        }
        if (currentCard.getCardValue().equals(drawnCard.getCardValue())) {
            return true;
        }
        if (drawnCard.getCardType().equals(WILD_CARD)) {
            return true;
        }
        if (currentCard.getCardType().equals(WILD_CARD)) {
            return drawnCard.getCardColor().equals(((WildCard) currentCard).getChosenColor());
        }
        return false;
    }

    /**
     * Skip the current player's turn and makes the next player to
     * be able to start his turn.
     */
    public void skipCurrentPlayerTurn() {
        Player currentPlayingPlayer = getCurrentPlayingPlayer();
        Player nextPlayer = getNextPlayer();
        currentPlayingPlayer.switchTurn();
        nextPlayer.switchTurn();
    }

    /**
     * Get the next possible player if current player ends his turn.
     * @return the next possible player
     */
    public Player getNextPlayer() {
        for (int i = 0; i < activePlayers.length; i++) {
            if (activePlayers[i].isTurnToPlay()) {
                return activePlayers[(i + 1) % activePlayers.length];
            }
        }
        return null;
    }

    /**
     * Get the current player that is playing his turn.
     * @return the current player
     */
    public Player getCurrentPlayingPlayer() {
        for (Player p : activePlayers) {
            if (p.isTurnToPlay()) {
                return p;
            }
        }
        return null;
    }

    /**
     * Draw multiple cards for the player.
     * @param numToDraw the number of cards to be drawn
     */
    public void playerDrawMultipleCards(int numToDraw) {
        Player p = getCurrentPlayingPlayer();
        for (int i = 0; i < numToDraw; i++) {
            p.addCardToHand(drawNewCardFromCardStack());
        }
    }

    /**
     * Check if the game is over, that is, a player no long has any card in hand.
     * @return True if the game is over, false otherwise
     */
    public boolean isOver() {
        for (Player p : activePlayers) {
            if (!p.hasCardInHand()) {
                gameIsOver = true;
                return true;
            }
        }
        return gameIsOver;
    }

    /**
     * Get the number of remaining cards in current card deck.
     * @return the number of remaining cards
     */
    public int numRemainingCardInDeck() {
        return cardStack.size();
    }

    /**
     * Print the current player name to indicate that it is his
     * turn to play to System.out
     */
    public void printCurrentPlayingPlayer() {
        Player p = getCurrentPlayingPlayer();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(p.getPlayerName() + "'s Turn");
    }

    /**
     * This is in the standard UNO rule, if the starting card is not a normal number card,
     * we should perform actions accordingly.
     * This is one of the custom rules in assignment 1.1 Part IV.
     * @param randomStartingCard the first card in card deck
     */
    public void specialActionForNonNumberStartingCard(UNOCard randomStartingCard) {
        if (randomStartingCard.getCardType().equals(WILD_CARD)) {
            int randomInt = (int) (Math.random() * 4);
            ((WildCard) randomStartingCard).chooseColor(UNO_COLORS[randomInt]);
        }
        if (randomStartingCard.getCardValue().equals(DRAW_TWO_CARD)) {
            System.out.println("The first card from the deck is a draw two card.");
            System.out.println("Player 1 has to draw two cards and skip his turn");
            playerDrawMultipleCards(2);
            skipCurrentPlayerTurn();
        }
        if (randomStartingCard.getCardValue().equals(SKIP_CARD)) {
            System.out.println("The first card from the deck is a skip card.");
            System.out.println("Player 1 has to skip his turn");
            skipCurrentPlayerTurn();
        }
        if (randomStartingCard.getCardValue().equals(REVERSE_CARD)) {
            System.out.println("The first card from the deck is a reverse card.");
            System.out.println("The playing order has been reversed and start from the last player");
            Collections.reverse(Arrays.asList(getActivePlayers()));
            skipCurrentPlayerTurn();
        }
    }


}
