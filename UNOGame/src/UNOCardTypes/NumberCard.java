package UNOCardTypes;

import GameInterface.UNOCardConstants;

import java.awt.*;

public class NumberCard extends UNOCard {
    public NumberCard(Color cardColor, String cardValue) {
        super(cardColor, UNOCardConstants.NUMBER_CARD, cardValue);
    }
}
