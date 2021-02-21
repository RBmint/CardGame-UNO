package UserInput;
import GameInterface.GameConstants;
import GameInterface.UNOCardConstants;
import Main.Game;
import Main.Player;
import Main.CardDealer;
import UNOCardTypes.UNOCard;
import UNOCardTypes.WildCard;

import java.awt.*;
import java.util.*;

/**
 * The class that starts the game and handle all the user inputs.
 */
public class PlayerInputs implements UNOCardConstants, GameConstants {
    private Game game;
    private Stack<UNOCard> discardPile;
    private boolean gameIsOver = false;
    private int misplayPenaltyCount = 0;

    /**
     * The default constructor will start the game, find one starting card
     * and bring the players into the main game loop.
     */
    public PlayerInputs() {
        game = new Game();
        discardPile = new Stack<>();
        UNOCard randomStartingCard = game.drawNewCardFromCardStack();
        while (randomStartingCard.getCardValue().equals(WILD_DRAW_FOUR_CARD)) {
            discardPile.add(randomStartingCard);
            randomStartingCard = game.drawNewCardFromCardStack();
        }
        game.specialActionForNonNumberStartingCard(randomStartingCard);
        discardPile.add(randomStartingCard);
        enterMainGameLoop();
    }

    /**
     * The main game loop. Ends the game and print out the winner when
     * a player no longer has any card in hand.
     */
    private void enterMainGameLoop() {
        while (!gameIsOver) {
            game.printCurrentPlayingPlayer();
            System.out.print("Current Card is: ");
            printCurrentCard(getCurrentCard());
            System.out.println("Deck has " + game.numRemainingCardInDeck() + " cards remaining");
            //System.out.println("discardPile has " + discardPile.size() + " cards in it");
            Player currentPlayingPlayer = game.getCurrentPlayingPlayer();
            if (currentPlayingPlayer.getPlayerName().contains("AI")) {
                System.out.println(currentPlayingPlayer.getPlayerName() + " has "
                        + currentPlayingPlayer.getNumCardInHand() + " cards in hand");
                aiPlayerPlaysOneCard();
            } else {
                printAllCardsInHand(currentPlayingPlayer.getAllCards(), false);
                int indexOfCardToPlay = getIndexOfCardToPlay();
                if (indexOfCardToPlay == 0) {
                    playerDecideIfPlayLastDrawnCard();
                } else {
                    playSelectedCard(currentPlayingPlayer.getAllCards().get(indexOfCardToPlay - 1));
                }
            }
        }
    }

    /**
     * The AI player will play one card if he has a playable card in hand.
     * The situation will be different for baseline and strategic AI players.
     */
    private void aiPlayerPlaysOneCard() {
        Player aiPlayer = game.getCurrentPlayingPlayer();
        boolean aiHasAPlayableCard = false;
        for (UNOCard cardInHand : aiPlayer.getAllCards()) {
            if (game.canBePlayedImmediately(getCurrentCard(), cardInHand)) {
                aiHasAPlayableCard = true;
            }
        }
        if (!aiHasAPlayableCard) {
            aiPlayerDrawsOneCard(aiPlayer.getPlayerName().contains("Strategic"));
        } else {
            if (aiPlayer.getPlayerName().contains("Strategic")) {
                strategicAIPlaysOneCard();
            } else {
                baselineAIPlaysOneCard();
            }
        }
    }

    /**
     * The strategic AI will play a card. He will try to play an action
     * card first if the next player is about to win (has less than two cards)
     * and then try to play a number card first instead of using a wild card.
     * At last, if a wild card has to be played, he will try to change the
     * color to the color that he has the most.
     */
    private void strategicAIPlaysOneCard() {
        boolean hasPlayedACard;
        hasPlayedACard = tryPlayActionCardFirstIfNextPlayerHasLessThanTwoCards();
        if (hasPlayedACard) {return;}
        hasPlayedACard = tryPlayNumberCardFirst();
        if (hasPlayedACard) {return;}
        hasPlayedACard = tryChangeToColorWithMostAdvantage();
        if (hasPlayedACard) {return;}
        baselineAIPlaysOneCard();
    }

    /**
     * The strategic AI will try to play an action card if the next
     * player is about to win.
     * @return True if the strategic AI has successfully played an action card.
     */
    private boolean tryPlayActionCardFirstIfNextPlayerHasLessThanTwoCards() {
        if (game.getNextPlayer().getNumCardInHand() <= 2) {
            return playTheCardInHand(ACTION_CARD);
        }
        return false;
    }

    /**
     * The strategic AI will try to play a normal number card and
     * try to save the wild card if possible.
     * @return True if the strategic AI has succesfully played a number card.
     */
    private boolean tryPlayNumberCardFirst() {
        return playTheCardInHand(NUMBER_CARD);
    }

    /**
     * The AI player will play a card from his hand.
     * @param numberCard the card to be played
     * @return True if the AI player successfully played this card.
     */
    private boolean playTheCardInHand(String numberCard) {
        for (UNOCard cardInHand : game.getCurrentPlayingPlayer().getAllCards()) {
            if (cardInHand.getCardType().equals(numberCard) &&
                    game.canBePlayedImmediately(getCurrentCard(), cardInHand)) {
                System.out.print(game.getCurrentPlayingPlayer().getPlayerName() + " has played ");
                printCurrentCard(cardInHand);
                playSelectedCard(cardInHand);
                return true;
            }
        }
        return false;
    }

    /**
     * The strategic AI player will try to choose the color of the
     * wild card to the color that he has the most in hand.
     * @return True if the strategic AI successfully played a wild card.
     */
    private boolean tryChangeToColorWithMostAdvantage() {
        UNOCard cardToBePlayed = null;
        for (UNOCard cardInHand : game.getCurrentPlayingPlayer().getAllCards()) {
            if (cardInHand.getCardType().equals(WILD_CARD)) {
                cardToBePlayed = cardInHand;
                break;
            }
        }
        if (cardToBePlayed != null) {
            Color toBePlayed = findCardWithMostColor();
            discardPile.add(cardToBePlayed);
            game.removePlayedCardFromPlayer(cardToBePlayed);
            checkGameStatus();
            if (gameIsOver) {
                return true;
            }
            ((WildCard) cardToBePlayed).chooseColor(toBePlayed);
            System.out.println("AI has chosen the color " + cardToBePlayed.colorToString(toBePlayed));
            checkIfCardIsAWildDrawFourCard((WildCard) cardToBePlayed);
            return true;
        }
        return false;
    }

    /**
     * Find the color that the strategic AI has the most in hands.
     * @return the color that strategic AI has the most.
     */
    private Color findCardWithMostColor() {
        int green = 0;
        int yellow = 0;
        int red = 0;
        int blue = 0;
        for (UNOCard cardInHand : game.getCurrentPlayingPlayer().getAllCards()) {
            if (cardInHand.getCardColor().equals(GREEN)) {
                green++;
            }
            if (cardInHand.getCardColor().equals(YELLOW)) {
                yellow++;
            }
            if (cardInHand.getCardColor().equals(RED)) {
                red++;
            }
            if (cardInHand.getCardColor().equals(BLUE)) {
                blue++;
            }
        }
        int max = green;        /*if any color has a tie, the default color will be green*/
        Color color = GREEN;
        if (yellow > max) {color = YELLOW;}
        if (red > max) {color = RED;}
        if (blue > max) {color = BLUE;}
        return color;
    }

    /**
     * The baseline AI will play a card from his hand. He will try to
     * play a random card from his hand if it is playable.
     */
    private void baselineAIPlaysOneCard() {
        Player aiPlayer = game.getCurrentPlayingPlayer();
        int randomInt = (int) (Math.random() * aiPlayer.getNumCardInHand());
        UNOCard randomCardToBeChecked = aiPlayer.getAllCards().get(randomInt);
        while (!game.canBePlayedImmediately(getCurrentCard(), randomCardToBeChecked)) {
            randomInt = (int) (Math.random() * aiPlayer.getNumCardInHand());
            randomCardToBeChecked = aiPlayer.getAllCards().get(randomInt);
        }
        System.out.print(aiPlayer.getPlayerName() + " has played ");
        printCurrentCard(randomCardToBeChecked);
        if (randomCardToBeChecked.getCardType().equals(WILD_CARD)) {
            aiPlayerRandomChooseColor(randomCardToBeChecked);
        } else {
            playSelectedCard(randomCardToBeChecked);
        }
    }

    /**
     * The AI player will draw a card. The baseline AI will just keep the card.
     * The strategic AI, however, will try to play the drawn card immediately
     * if that is applicable.
     * @param isStrategicAI if the AI player is strategic.
     */
    private void aiPlayerDrawsOneCard(boolean isStrategicAI) {
        checkIfDeckNeedShuffle(1);
        UNOCard drawnCard = game.drawNewCardFromCardStack();
        game.getCurrentPlayingPlayer().addCardToHand(drawnCard);
        System.out.println("AI player draws a card from the deck");
        if (isStrategicAI) {
            if (game.canBePlayedImmediately(getCurrentCard(), drawnCard) &&
                    !drawnCard.getCardType().equals(WILD_CARD)) {
                System.out.println("AI chooses to play the drawn card immediately");
                System.out.print(game.getCurrentPlayingPlayer().getPlayerName() + " has played ");
                printCurrentCard(drawnCard);
                playSelectedCard(drawnCard);
            }
            game.skipCurrentPlayerTurn();
        } else {
            game.skipCurrentPlayerTurn();
        }
    }

    /**
     * The baseline AI will choose the color of the wild card randomly.
     * @param selectedCard the wild card that was played.
     */
    private void aiPlayerRandomChooseColor(UNOCard selectedCard) {
        discardPile.add(selectedCard);
        game.removePlayedCardFromPlayer(selectedCard);
        checkGameStatus();
        if (gameIsOver) {return;}
        int randomInt = (int) (Math.random() * 4);
        ((WildCard) selectedCard).chooseColor(UNO_COLORS[randomInt]);
        System.out.println("AI has chosen the color" + selectedCard.colorToString(UNO_COLORS[randomInt]));
        checkIfCardIsAWildDrawFourCard((WildCard) selectedCard);
    }

    /**
     * Handle the scanner to get which card the user wants to play.
     * @return the index of the card that the user wants to play
     */
    private int getIndexOfCardToPlay() {
        Scanner scanner = new Scanner(System.in);
        int indexOfCardToPlay;
        while (true) {
            String input = scanner.nextLine();
            try {
                indexOfCardToPlay = Integer.parseInt(input);
                if (indexOfCardToPlay > game.getCurrentPlayingPlayer().getNumCardInHand()) {
                    System.out.println("Invalid card index.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number, please re-enter");
            }
        }
        return indexOfCardToPlay;
    }

    /**
     * Handle the situation when player choose to draw a card from the deck,
     * and he may immediately play this card or choose not to.
     */
    private void playerDecideIfPlayLastDrawnCard() {
        Player currentPlayingPlayer = game.getCurrentPlayingPlayer();
        checkIfDeckNeedShuffle(1);
        game.playerDrawOneCard(getCurrentCard());
        if (game.canBePlayedImmediately(getCurrentCard(), currentPlayingPlayer.getLastDrawnCard())) {
            Scanner scanner = new Scanner(System.in);
            printAllCardsInHand(currentPlayingPlayer.getAllCards(), true);
            while(true) {
                String input = scanner.nextLine();
                if (input.equals("yes")) {
                    playSelectedCard(currentPlayingPlayer.getLastDrawnCard());
                    break;
                } else if (input.equals("no")) {
                    game.skipCurrentPlayerTurn();
                    break;
                } else {
                    System.out.println("Invalid input, please choose from yes or no");
                }
            }
        }
    }

    /**
     * Play the selected card and take corresponding actions.
     * @param selectedCard the card to be played
     */
    private void playSelectedCard(UNOCard selectedCard) {
        if (game.canBePlayedImmediately(getCurrentCard(), selectedCard)) {
            discardPile.add(selectedCard);
            game.removePlayedCardFromPlayer(selectedCard);
            checkGameStatus();
            if (gameIsOver) {return;}
            if (selectedCard.getCardType().equals(NUMBER_CARD)) {
                game.skipCurrentPlayerTurn();
            }
            if (selectedCard.getCardType().equals(ACTION_CARD)) {
                performAction(selectedCard);
            }
            if (selectedCard.getCardType().equals(WILD_CARD)) {
                performWildCardColorSelection((WildCard) selectedCard);
            }
        } else if (misplayPenaltyCount < 1){
            System.out.println("Card selection is invalid. You have one more chance before a penalty.");
            misplayPenaltyCount++;
        } else {
            penaltyDrawOneCardAndSkipTurn();
            misplayPenaltyCount = 0;
        }
    }

    /**
     * Perform the wild card that allows player to input their desired color,
     * will also draw 4 cards for next player and skip his turn if the wild card
     * is a wild draw 4 card.
     * @param wildCard the card played
     */
    private void performWildCardColorSelection(WildCard wildCard) {
        System.out.println("Please choose your desired color");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("green")) {
                wildCard.chooseColor(GREEN);
                discardPile.add(wildCard);
                break;
            }
            if (input.equals("red")) {
                wildCard.chooseColor(RED);
                discardPile.add(wildCard);
                break;
            }
            if (input.equals("blue")) {
                wildCard.chooseColor(BLUE);
                discardPile.add(wildCard);
                break;
            }
            if (input.equals("yellow")) {
                wildCard.chooseColor(YELLOW);
                discardPile.add(wildCard);
                break;
            }
            System.out.println("Input color is invalid. Please choose from green, red, blue, yellow");
        }
        game.skipCurrentPlayerTurn();
        checkIfCardIsAWildDrawFourCard(wildCard);
    }

    /**
     * Check if the card is a wild draw four card. If it is,
     * the next player will have to draw four cards.
     * @param wildCard the card to be checked
     */
    private void checkIfCardIsAWildDrawFourCard(WildCard wildCard) {
        if (wildCard.getCardValue().equals(WILD_DRAW_FOUR_CARD)) {
            checkIfDeckNeedShuffle(4);
            game.playerDrawMultipleCards(4);
            game.skipCurrentPlayerTurn();
            System.out.println(game.getCurrentPlayingPlayer().getPlayerName()
                    + " have to draw four cards and skip his turn.");
        }
    }

    /**
     * Check if the game is over after a player plays out a card.
     */
    private void checkGameStatus() {
        if (game.isOver()) {
            gameIsOver = true;
            System.out.println("Game is over, "
                    + game.getCurrentPlayingPlayer().getPlayerName() + " wins!!!");
        }
    }

    /**
     * Print all cards that a player has out to System.out. If the player has chose
     * to draw a card, he can no longer choose to draw one more but will be allowed
     * to decide whether to play the card he just drawn.
     * @param cardsToPrint the cards of a player
     * @param hasDrawnACard if the player has drawn a card
     */
    private void printAllCardsInHand(LinkedList<UNOCard> cardsToPrint, boolean hasDrawnACard) {
        System.out.println("You have the following options:");
        if (!hasDrawnACard) {
            System.out.println("0. Draw a new card");
        }
        int cardNum = 1;
        for(UNOCard unoCard : cardsToPrint) {
            System.out.print(cardNum + ". ");
            printCurrentCard(unoCard);
            cardNum++;
        }
        if (!hasDrawnACard) {
            System.out.println("Please type the index of card you want to play: ");
        } else {
            System.out.println("You can choose whether to play the card you just drawn");
            System.out.println("Please indicate by typing yes or no");
        }
    }

    /**
     * Print one card to System.out.
     * @param cardToPrint the card to be printed
     */
    private void printCurrentCard(UNOCard cardToPrint) {
        if (cardToPrint.getCardType().equals(NUMBER_CARD) || cardToPrint.getCardType().equals(ACTION_CARD)) {
            System.out.println(cardToPrint.colorToString(cardToPrint.getCardColor())
                    + " " + cardToPrint.getCardValue());
        }
        if (cardToPrint.getCardType().equals(WILD_CARD) || cardToPrint.getCardType().equals(WILD_DRAW_FOUR_CARD)) {
            if (((WildCard) cardToPrint).getChosenColor() == null) {
                System.out.println(cardToPrint.getCardValue());
            } else {
                System.out.println(cardToPrint.getCardValue() +
                        cardToPrint.colorToString(((WildCard) cardToPrint).getChosenColor()));
            }
        }
    }

    /**
     * Get the current card that the players need to follow
     * @return the card that the players need to follow
     */
    private UNOCard getCurrentCard() {
        return discardPile.peek();
    }

    /**
     * Check if the current deck will have less than 0 card after performing the draw
     * and shuffle the discard pile into the deck if necessary.
     * @param numToDraw the number of cards needed to draw to compare with the deck
     */
    private void checkIfDeckNeedShuffle(int numToDraw) {
        if (game.numRemainingCardInDeck() < numToDraw) {
            game.cardDealer = new CardDealer(discardPile);
            game.cardStack = game.cardDealer.createCardStackFromCardDeck();
            System.out.println("Deck has been re-shuffled");
        }
    }

    /**
     * Perform draw two, skip, or reverse action.
     * @param actionCard the card played
     */
    private void performAction(UNOCard actionCard) {
        if (actionCard.getCardValue().equals(DRAW_TWO_CARD)) {
            game.skipCurrentPlayerTurn();
            checkIfDeckNeedShuffle(2);
            game.playerDrawMultipleCards(2);
            System.out.println(game.getCurrentPlayingPlayer().getPlayerName()
                    + " have to draw two cards and skip his turn.");
            game.skipCurrentPlayerTurn();
        }
        if (actionCard.getCardValue().equals(SKIP_CARD)) {
            game.skipCurrentPlayerTurn();
            System.out.println(game.getCurrentPlayingPlayer().getPlayerName()
                    + " have to skip his turn.");
            game.skipCurrentPlayerTurn();
        }
        if (actionCard.getCardValue().equals(REVERSE_CARD)) {
            System.out.println("The order of how player should play has been reversed");
            Collections.reverse(Arrays.asList(game.getActivePlayers()));
            if (game.getActivePlayers().length != 2) {
                game.skipCurrentPlayerTurn();
            }
        }
    }

    /**
     * The player has input two invalid cards in a row. He will have to draw a card
     * from the card deck and automatically miss this turn as a penalty. This is
     * another custom rule in assignment 1.1 Part IV.
     */
    private void penaltyDrawOneCardAndSkipTurn() {
        System.out.println("You chose the invalid card twice in a row. " +
                "You have to draw a card and end your turn.");
        checkIfDeckNeedShuffle(1);
        Player p = game.getCurrentPlayingPlayer();
        UNOCard drawnCard = game.drawNewCardFromCardStack();
        p.addCardToHand(drawnCard);
        game.skipCurrentPlayerTurn();
    }
}
