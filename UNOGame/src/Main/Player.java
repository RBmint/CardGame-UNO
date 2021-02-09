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

    public void switchTurn() {
        isTurnToPlay = !isTurnToPlay;
    }
    public boolean isTurnToPlay() {
        return isTurnToPlay;
    }
    public boolean hasThisCard(UNOCard thisCard) {
        return myCards.contains(thisCard);
    }
    public void removeCardFromHand(UNOCard thisCard) {
        myCards.remove(thisCard);
    }
    public String getPlayerName() {
        return playerName;
    }
    public void addCardToHand(UNOCard unoCard) {
        myCards.add(unoCard);
    }
    public int getNumCardInHand() {
        return myCards.size();
    }
    public boolean hasCardInHand() {
        return !myCards.isEmpty();
    }
    public LinkedList<UNOCard> getAllCards() {
        return myCards;
    }
    public UNOCard getLastDrawnCard() {
        return myCards.getLast();
    }
}
