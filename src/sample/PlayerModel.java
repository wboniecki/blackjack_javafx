package sample;

import java.util.ArrayList;
import java.util.List;


public abstract class PlayerModel {
    protected List<CardModel> hand = new ArrayList<>();
    protected int score;
    protected int[] altScore = new int[2];
    protected int deposit;
    protected String ownCardNames;
    protected int hiddenScore;
    protected  int[] altHiddenScore = new int[2];

    public int getScore() {
        return score;
    }

   protected void setScore(int score) {
        this.score = score;
    }

    protected abstract void computeScore(CardModel card);

    public int getDeposit() {
        return deposit;
    }

    public void addDeposit(int deposit) {
        this.deposit += deposit;
    }

    public void subDeposit(int deposit) {
        this.deposit -= deposit;
    }

    public String getOwnCardNames() {
        return ownCardNames;
    }

    public abstract void addCardToHand(CardModel card);

    public void updateCardNames(String name){
        ownCardNames += name+"\n";
    }


    public void newGame(int deposit) {
        reset();
        this.deposit = deposit;
    }

    public void newRound() {
        ownCardNames="";
        score = 0;
        altScore[0]=0;
        altScore[1]=0;
        hiddenScore=0;
        altHiddenScore[0]=0;
        altHiddenScore[1]=0;
        hand.clear();
    }

    public void reset() {
        ownCardNames="";
        deposit=0;
        score = 0;
        altScore[0]=0;
        altScore[1]=0;
        hiddenScore=0;
        altHiddenScore[0]=0;
        altHiddenScore[1]=0;
        hand.clear();
    }


}
