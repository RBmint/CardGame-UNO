package UNOCardTypes;

import GameInterface.UNOCardConstants;

import java.awt.*;

/**
 * This class is in charge of constructing a wild card.
 */
public class WildCard extends UNOCard {
    private Color chosenColor;

    /**
     * Create a wild card based on the card type (WildDrawFour or Wild).
     * @param cardValue the card type to be created.
     */
    public WildCard(String cardValue) {
        super(UNOCardConstants.BLACK, UNOCardConstants.WILD_CARD, cardValue);
    }

    /**
     * Choose the intended color for the wild card.
     * @param color the color to be chosen.
     */
    public void chooseColor(Color color) {
        chosenColor = color;
    }

    /**
     * Get the chosen color of current wild card.
     * @return the color that was selected.
     */
    public Color getChosenColor() {
        return chosenColor;
    }
}
