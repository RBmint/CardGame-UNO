package UNOCardTypes;

import GameInterface.UNOCardConstants;

import java.awt.*;

public abstract class UNOCard implements UNOCardConstants {

    private Color cardColor = null;
    private String cardType = null;
    private String cardValue = null;
    public UNOCard(Color cardColor, String cardType, String cardValue) {
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.cardValue = cardValue;
    }
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
    public Color getCardColor() {
        return cardColor;
    }
    public String getCardType() {
        return cardType;
    }
    public String getCardValue() {
        return cardValue;
    }
}
