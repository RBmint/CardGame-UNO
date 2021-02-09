package Main;

import GameInterface.GameConstants;
import UNOCardTypes.UNOCard;

import java.util.LinkedList;
import java.util.Stack;

/**
 * This class will deal the card to the players at the start of the game
 * after the CardDeck class initiates the card deck.
 */
public class CardDealer implements GameConstants {
    private CardDeck cardDeck;
    private Stack<UNOCard> cardStack;

    /**
     * The default constructor create a new card deck for a brand new game.
     */
    public CardDealer() {
        this.cardDeck = new CardDeck();
    }

    /**
     * The constructor pass the discard pile to the card deck constructor
     * so that it could shuffle the discard pile into the current card deck
     * @param discardPile the discard pile to be passed
     */
    public CardDealer(Stack<UNOCard> discardPile) {
        this.cardDeck = new CardDeck(discardPile);
    }

    /**
     * Create a card stack from the card deck, which is a linked list.
     * @return the stack of the current card deck
     */
    public Stack<UNOCard> createCardStackFromCardDeck() {
        LinkedList <UNOCard> cardDeckToAdd = cardDeck.getCardDeck();
        cardStack = new Stack<>();
        cardStack.addAll(cardDeckToAdd);
        return cardStack;
    }

    /**
     * Deal the initial seven cards to each player
     * @param players players to be dealt
     */
    public void dealCardToPlayers(Player[] players) {
        for(Player p : players) {
            for (int i = 0; i < STARTING_HAND; i++) {
                p.addCardToHand(cardStack.pop());
            }
        }
    }

    /**
     * Draw a new card from the card deck, thus a new card from card stack.
     * @return the card that has been drawn
     */
    public UNOCard getNewCardFromCardDeck() {
        return cardStack.pop();
    }
}
