package sample;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * All action ingame
 */
public class GameController {
    private static final int NEW_GAME = 0;
    private static final int ERR_BET = 1;
    private static final int START_GAME = 2;
    private GameModel model;
    private DeckModel deck;
    private Player player;
    private Croupier croupier;

    public GameController() {
        model = new GameModel();
        deck = new DeckModel();
        player = new Player();
        croupier = new Croupier();
    }

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

    public void setNewRound() {
        player.newRound();
        croupier.newRound();
        deck.reset();
        deck.newDeck();
        model.newRound();
    }

    public String getPlayerDeposit() {
        return Integer.toString(player.getDeposit())+"$";
    }

    public String getCroupierDeposit() {
        return Integer.toString(croupier.getDeposit())+"$";
    }

    public String getPlayerScore() {
        return Integer.toString(player.getScore());
    }
    public String getCroupierScore() {
        return Integer.toString(croupier.getHiddenScore());
    }

    public String getCroupierFullScore() {
        return Integer.toString(croupier.getScore());
    }

    public String getPlayerCardOwn() {
        return player.getOwnCardNames();
    }

    public String getCroupierCardOwn() {
        return croupier.getOwnCardNames();
    }

    public String getDeckCount() {
        return Integer.toString(deck.getDeckCount());
    }

    public String getGameLog() {
        return model.getGameLog();
    }

    public void drawPlayerCard() {
        CardModel topCard = deck.drawCard();
        player.addCardToHand(topCard);
        model.updateDrawCardLogPlayer(3,topCard.getName());
    }

    public void drawCroupierCard() {
        CardModel topCard = deck.drawCard();
        if(croupier.getScore()==0){
            model.updateDrawCardLogCroupier(4,topCard.getName(),true);
        } else {
            model.updateDrawCardLogCroupier(4,topCard.getName(),false);
        }
        croupier.addCardToHand(topCard);
    }

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
