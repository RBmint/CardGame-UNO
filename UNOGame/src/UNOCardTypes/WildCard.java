package UNOCardTypes;

import GameInterface.UNOCardConstants;

import java.awt.*;

public class WildCard extends UNOCard {
    private Color chosenColor;

    public WildCard(String cardValue) {
        super(UNOCardConstants.BLACK, UNOCardConstants.WILD_CARD, cardValue);
    }

    public void chooseColor(Color color) {
        chosenColor = color;
    }

    public Color getChosenColor() {
        return chosenColor;
    }
}
