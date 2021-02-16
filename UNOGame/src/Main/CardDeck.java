package Main;

import GameInterface.GameConstants;
import UNOCardTypes.ActionCard;
import UNOCardTypes.NumberCard;
import UNOCardTypes.UNOCard;
import UNOCardTypes.WildCard;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

/**
 * This class is responsible for initiating a card deck and a
 * corresponding card stack that keeps track of the game.
 */
public class CardDeck implements GameConstants {

    private LinkedList<UNOCard> UNOCards;

    /**
     * The default constructor initiate a brand new game.
     */
    public CardDeck() {
        UNOCards = new LinkedList<>();
        initiateCardDeck();
    }

    /**
     * The constructor shuffle the card from the discard pile
     * and re-construct the deck as well as the card stack from it.
     * @param discardPile the stack to re-construct from
     */
    public CardDeck(Stack<UNOCard> discardPile) {
        UNOCards = new LinkedList<>();
        UNOCard currentCard = discardPile.pop();
        while (!discardPile.empty()) {
            UNOCards.add(discardPile.pop());
        }
        discardPile.add(currentCard);
        Collections.shuffle(UNOCards);
    }

    /**
     * Add number cards, action cards, wild cards to the deck then shuffles it.
     */
    private void initiateCardDeck() {
        addNumberCards();
        addActionCards();
        addWildCards();
        shuffleCardDeck();
        //System.out.println("Deck Initiated");
    }

    /**
     * Get the current card deck as a linked list.
     * @return the current card deck
     */
    public LinkedList<UNOCard> getCardDeck() {
        return UNOCards;
    }

    /**
     * Add each color, number 1 through 9 twice, while adding number 0
     * only once to the deck.
     */
    public void addNumberCards() {
        for(Color color : UNO_COLORS) {
            for (int num : UNO_NUMBERS) {
                UNOCards.add(new NumberCard(color, Integer.toString(num)));
                if (num != 0) {
                    UNOCards.add(new NumberCard(color, Integer.toString(num)));
                }
            }
        }
    }

    /**
     * Add each action card corresponding to each color twice to the deck.
     */
    public void addActionCards() {
        for(Color color : UNO_COLORS) {
            for(String cardTypes : ACTION_CARD_TYPES) {
                UNOCards.add(new ActionCard(color, cardTypes));
                UNOCards.add(new ActionCard(color, cardTypes));
            }
        }
    }

    /**
     * Add four wild cards each to the deck.
     */
    public void addWildCards() {
        for (int i = 0; i < WILD_CARD_COUNT; i++) {
            for (String cardTypes : WILD_CARD_TYPES) {
                UNOCards.add(new WildCard(cardTypes));
            }
        }
    }

    /**
     * Shuffle the current deck.
     */
    private void shuffleCardDeck() {
        Collections.shuffle(UNOCards);
    }
}
