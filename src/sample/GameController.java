package sample;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Main controller in game, contains all game objects and actions.
 * Actions is triggered by ViewController.java
 * some functions was wrote in early state of app and some game logic can still be in controller
 */
public class GameController {

    //this static variables was defined in early dev process
    //all log was operated by GameModel.java
    private static final int NEW_GAME = 0;
    private static final int ERR_BET = 1;
    private static final int START_GAME = 2;

    /**
     * Declaration of objects using in game
     * GameModel - contains the model of game, win/lose statements, game log actions, compute scores, etc.
     * DeckModel - contains model of deck, card names, card value etc.
     * Player - object of player based on PlayerModel.java
     * Croupier - object of croupier based on PlayerModel.java
     */
    private GameModel model;
    private DeckModel deck;
    private Player player;
    private Croupier croupier;

    /**
     * Contructor
     */
    public GameController() {
        model = new GameModel();
        deck = new DeckModel();
        player = new Player();
        croupier = new Croupier();
    }

    /**
     * setNewGame() - this action is trigger when player start new game
     * reset all game variables, like player score ,deck array or bool variables (like isOver)
     */
    public void setNewGame() {
        player.reset();
        player.addDeposit(100);
        croupier.reset();
        croupier.addDeposit(10000);
        deck.reset();
        deck.newDeck();
        model.reset();
        model.updateLog(NEW_GAME);
    }

    /**
     * setNewRound() - this function works similar to setNewGame() but do not clear player and croupier deposits
     * trigger when player start new round
     */
    public void setNewRound() {
        player.newRound();
        croupier.newRound();
        deck.reset();
        deck.newDeck();
        model.newRound();
    }
    // return palyer deposit (to view)
    public String getPlayerDeposit() {
        return Integer.toString(player.getDeposit())+"$";
    }

    //return croupier deposit (to view)
    public String getCroupierDeposit() {
        return Integer.toString(croupier.getDeposit())+"$";
    }

    //return player score (to view)
    public String getPlayerScore() {
        return Integer.toString(player.getScore());
    }

    //return croupier hidden score (before end of turn)
    public String getCroupierScore() {
        return Integer.toString(croupier.getHiddenScore());
    }

    //return croupier score (after end of turn)
    public String getCroupierFullScore() {
        return Integer.toString(croupier.getScore());
    }

    //return names of cards owning by player
    public String getPlayerCardOwn() {
        return player.getOwnCardNames();
    }

    //return names of cards owning by croupier
    public String getCroupierCardOwn() {
        return croupier.getOwnCardNames();
    }

    //return number of remaining cards in deck
    public String getDeckCount() {
        return Integer.toString(deck.getDeckCount());
    }

    //return the game action log string
    public String getGameLog() {
        return model.getGameLog();
    }

    /**
     * drawPlayerCard() - this action draw card from deck and forward it to the Player hand,
     * at the end tells the model to update game log of action with card name
     */
    public void drawPlayerCard() {
        CardModel topCard = deck.drawCard();
        player.addCardToHand(topCard);
        model.updateDrawCardLogPlayer(3,topCard.getName());
    }

    /**
     * drawCroupierCard() - similar action to drawPlayerCard() but here was coded some game logic ( :( )
     * model.updateDrawCardLogCroupier(4, top.Card.getName(), croupier.getScore()) will be better
     */
    public void drawCroupierCard() {
        CardModel topCard = deck.drawCard();
        if(croupier.getScore()==0){
            model.updateDrawCardLogCroupier(4,topCard.getName(),true);
        } else {
            model.updateDrawCardLogCroupier(4,topCard.getName(),false);
        }
        croupier.addCardToHand(topCard);
    }

    /**
     * another game logic in controller...
     *
     * @param bet - value from textArea from view
     * @return boolean value - is bet ok or not
     *
     * this function checking player bet before game start (min. 10$, max. player or croupier deposit
     */
    public boolean checkBet(String bet) {
        int betVal = Integer.parseInt(bet);
        if(betVal < 10 || betVal > player.getDeposit() || betVal > croupier.getDeposit()) {
            model.updateLog(ERR_BET);
            return false;
        } else {
            return true;
        }
    }

    public void roundStart(String bet) {
        int betVal = Integer.parseInt(bet);
        player.subDeposit(betVal);
        croupier.subDeposit(betVal);
        model.setGameDeposit(2*betVal);
        model.updateLog(START_GAME);
        drawPlayerCard();
        drawPlayerCard();
        drawCroupierCard();
        drawCroupierCard();
        checkWin();
    }

    public void checkWin() {
        model.isPlayerRoundEnd(player.getScore());
        if (isPlayerWait()) {
            endTurn();
        }
    }

    public String getGameDeposit() {
        return Integer.toString(model.getGameDeposit())+"$";
    }



    public boolean isPlayerWait() {
        return model.isPlayerWait();
    }

    public void playerWait() {
        model.playerWait();
        endTurn();
    }

    private void endTurn() {
        croupier.showCard();
        while (!model.isCroupierWait(croupier.getScore())){
            drawCroupierCard();
        }
        model.computeWinner(croupier.getScore(), player.getScore());
        if(model.getWinner() == 1) {
            player.addDeposit(model.getGameDeposit());
        }
        if(model.getWinner() == 2) {
            croupier.addDeposit(model.getGameDeposit());
        }
        if(model.getWinner() == 3) {
            player.addDeposit(model.getGameDeposit()/2);
            croupier.addDeposit(model.getGameDeposit()/2);
        }
        checkGameOver();
    }

    private boolean checkGameOver() {
        boolean isOver;
        if(model.checkGameOver(croupier.getDeposit(), player.getDeposit())) {
            isOver=true;
        } else {
            isOver=false;
        }
        return isOver;
    }

    public boolean isGameOver() {
        return model.getOver();
    }
}
