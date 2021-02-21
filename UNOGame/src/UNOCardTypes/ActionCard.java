package UNOCardTypes;

import GameInterface.UNOCardConstants;

import java.awt.*;

/**
 * This class is in charge of constructing an action card.
 */
public class ActionCard extends UNOCard {
    /**
     * Create an action card based on the color and value.
     * @param cardColor the color to be created.
     * @param cardValue the value (action) to be created.
     */
    public ActionCard(Color cardColor, String cardValue) {
        super(cardColor, UNOCardConstants.ACTION_CARD, cardValue);
    }
}
