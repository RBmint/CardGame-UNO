package UNOCardTypes;

import GameInterface.UNOCardConstants;

import java.awt.*;

/**
 * This abstract class is in charge of constructing any UNO card
 * and is going to be extended by a specific card class.
 */
public abstract class UNOCard implements UNOCardConstants {

    private Color cardColor = null;
    private String cardType = null;
    private String cardValue = null;

    /**
     * Assign the incoming parameters to the card.
     * @param cardColor the color of the card.
     * @param cardType the type of the card.
     * @param cardValue the value of the card.
     */
    public UNOCard(Color cardColor, String cardType, String cardValue) {
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.cardValue = cardValue;
    }

    /**
     * Convert the java style color to java style string.
     * @param cardColor the color to be converted.
     * @return the string representing the color
     */
    public String colorToString(Color cardColor) {
        if (cardColor == GREEN) {
            return "GREEN";
        }
        if (cardColor == RED) {
            return "RED";
        }
        if (cardColor == YELLOW) {
            return "YELLOW";
        }
        if (cardColor == BLUE) {
            return "BLUE";
        }
        return null;
    }

    /**
     * Get the color of the current card.
     * @return the color of the card.
     */
    public Color getCardColor() {
        return cardColor;
    }

    /**
     * Get the type of the current card.
     * @return the type of the card.
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Get the value of the current card.
     * @return the value of the card.
     */
    public String getCardValue() {
        return cardValue;
    }
}
