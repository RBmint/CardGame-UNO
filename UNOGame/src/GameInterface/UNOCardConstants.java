package GameInterface;

import java.awt.*;

/**
 * This class contains the crucial constants for the UNO cards.
 */
public interface UNOCardConstants {
    Color RED = new Color(255,0,0);
    Color GREEN = new Color(0,255,0);
    Color BLUE = new Color(0,255,255);
    Color YELLOW = new Color(255,255,0);
    Color BLACK = new Color(0,0,0);

    int WILD_CARD_COUNT = 4;

    String NUMBER_CARD = "numCard";

    String ACTION_CARD = "actCard";

    String REVERSE_CARD = "Reverse";
    String SKIP_CARD = "Skip";
    String DRAW_TWO_CARD = "DrawTwo";

    String WILD_CARD = "WildCard";
    String WILD_DRAW_FOUR_CARD = "WildDrawFour";
}
