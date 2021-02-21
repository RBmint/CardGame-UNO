package Main;

import UNOCardTypes.UNOCard;
import java.util.LinkedList;

/**
 * The class that are in charge of the players activities.
 * The class is built with simple get/check is methods.
 */
public class Player {
    private String playerName = null;
    private boolean isTurnToPlay = false;
    private LinkedList<UNOCard> myCards;
    /**
     * The constructor will initiate a player instance with name.
     * @param newName the name to be assigned
     */
    public Player(String newName) {
        playerName = newName;
        myCards = new LinkedList<>();
    }

    /**
     * Switch the player's turn to play (or not).
     */
    public void switchTurn() {
        isTurnToPlay = !isTurnToPlay;
    }

    /**
     * Check if it is the current player's turn to play.
     * @return True if it is, false otherwise.
     */
    public boolean isTurnToPlay() {
        return isTurnToPlay;
    }

    /**
     * Check if player has this particular card in his hand.
     * @param thisCard the card to be checked
     * @return True if the player has it, false otherwise.
     */
    public boolean hasThisCard(UNOCard thisCard) {
        return myCards.contains(thisCard);
    }

    /**
     * Remove a particular card from the player's hand.
     * @param thisCard the card to be removed.
     */
    public void removeCardFromHand(UNOCard thisCard) {
        myCards.remove(thisCard);
    }

    /**
     * Get the name of the player.
     * @return name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Add a particular card to the player's hand.
     * @param unoCard the card to be added.
     */
    public void addCardToHand(UNOCard unoCard) {
        myCards.add(unoCard);
    }

    /**
     * Get the number of cards a player currently holds.
     * @return The number of cards in hand.
     */
    public int getNumCardInHand() {
        return myCards.size();
    }

    /**
     * Check if a player has any cards in hand at all.
     * @return True if he has, false otherwise.
     */
    public boolean hasCardInHand() {
        return !myCards.isEmpty();
    }

    /**
     * Get all cards in a player's hand.
     * @return The list of cards in a player's hand.
     */
    public LinkedList<UNOCard> getAllCards() {
        return myCards;
    }

    /**
     * Get the card that is the last drawn one from a player.
     * @return the card that has been drawn most recently.
     */
    public UNOCard getLastDrawnCard() {
        return myCards.getLast();
    }
}
