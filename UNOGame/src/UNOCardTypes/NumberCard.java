package UNOCardTypes;

import GameInterface.UNOCardConstants;

import java.awt.*;

/**
 * This class is in charge of constructing a number card.
 */
public class NumberCard extends UNOCard {
    /**
     * Create a number card based on the color and value.
     * @param cardColor the color to be created.
     * @param cardValue the value (number) to be created.
     */
    public NumberCard(Color cardColor, String cardValue) {
        super(cardColor, UNOCardConstants.NUMBER_CARD, cardValue);
    }
}
