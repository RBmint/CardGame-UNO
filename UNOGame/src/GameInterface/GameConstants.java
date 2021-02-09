package GameInterface;

import java.awt.*;

public interface GameConstants extends UNOCardConstants {
    Color[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};
    int STARTING_HAND = 7;
    int[] UNO_NUMBERS ={0,1,2,3,4,5,6,7,8,9};
    String[] ACTION_CARD_TYPES = {REVERSE_CARD, SKIP_CARD, DRAW_TWO_CARD};
    String[] WILD_CARD_TYPES = {WILD_CARD, WILD_DRAW_FOUR_CARD};
}
