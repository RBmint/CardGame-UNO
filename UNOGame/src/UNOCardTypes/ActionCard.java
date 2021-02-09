package UNOCardTypes;

import GameInterface.UNOCardConstants;

import java.awt.*;

public class ActionCard extends UNOCard {
    public ActionCard(Color cardColor, String cardValue) {
        super(cardColor, UNOCardConstants.ACTION_CARD, cardValue);
    }
}
