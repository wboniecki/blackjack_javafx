package sample;
/**
 * main game model
 * here is define core logic of game, all game log statements
 *
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class GameModel {
    private String gameLog;
    private List<GameLog> log;
    private int gameDeposit;
    private boolean isPlayerWait;
    private boolean isCroupierWait;
    private int winner;
    private boolean isOver;


    /**
     * after player pass, croupier must draw if he score under 17
     * if player lose croupier instant win, whatever how much score he have
     * @param score
     * @return
     *
     */
    public boolean isCroupierWait(int score) {
        if (score<17 && winner!=2) {
            isCroupierWait = false;
        } else {
            isCroupierWait = true;
        }
        return isCroupierWait;
    }

    //check player points after draw card and set if player won(set blackjack) or lose
    public void isPlayerRoundEnd(int score) {
        if (score == 21) {
            isPlayerWait = true;
            updateLog(14);
        }
        if (score > 21) {
            isPlayerWait = true;
            winner = 2;
            updateLog(6);
        }
    }

    /**
     *
     * @param croupierSccore
     * @param playerScore
     *
     * compute winner based on croupierScore and playerScore
     * set winner in variable:
     * 0-none
     * 1-player
     * 2-croupier
     * 3-draw
     */
    public void computeWinner(int croupierSccore, int playerScore) {
        if (croupierSccore == 21 && playerScore==21) {
            winner = 3;
            updateLog(15);
        }
        if (croupierSccore > 21 && playerScore<=21) {
            winner = 1;
            updateLog(5);
        }
        if ((croupierSccore <= 21 && playerScore < 21) || (croupierSccore < 21 && playerScore <= 21)) {
            if(21-croupierSccore < 21-playerScore) {
                winner = 2;
                updateLog(6);
            } else {
                if(croupierSccore==playerScore) {
                    winner =3;
                    updateLog(15);
                } else {
                    winner = 1;
                    updateLog(5);
                }
            }


        }
    }

    public int getWinner() {
        return winner;
    }

    /**
     * Class contructor set default variables in game
     * defines primitive log array
     */
    public GameModel() {
        gameLog = "";
        gameDeposit = 0;
        log = new ArrayList<>();
        isPlayerWait = false;
        isCroupierWait = false;
        winner = 0;
        isOver = false;
        log.add(new GameLog("NEW_GAME", "Rozpoczynasz nowa gre!\n")); //0
        log.add(new GameLog("ERR_BET", "Stawka musi byc wieksza niz 10$ oraz nieprzekraczac wartosci depozytu Twojego i krupiera.\n")); //1
        log.add(new GameLog("START_GAME", "Gra rozpoczeta! W puli do zgarniecia jest: ")); //2
        log.add(new GameLog("PLAYER_DRAW", "Dobierasz karte: ")); //3
        log.add(new GameLog("CROUPIER_DRAW", "Krupier dobiera karte: ")); //4
        log.add(new GameLog("PLAYER_WIN", "Gratulacje! Wygrywasz: ")); //5
        log.add(new GameLog("CROUPIER_WIN", "Przykro mi! Przegrywasz.\n")); //6
        log.add(new GameLog("GAME_WIN","GRATULACJE! KRUPIER NIE MA SRODKOW DO GRY! WYGRALES\n")); //7
        log.add(new GameLog("GAME_OVER","KONIEC GRY! PRZEGRALES!\n")); //8
        log.add(new GameLog("PASS","Pasujesz.\n")); //9
        log.add(new GameLog("PLAYER_SCORE","Twoj wynik to: ")); //10
        log.add(new GameLog("PASS","Wynik krupiera to: ")); //11
        log.add(new GameLog("CROUPIER_PASS","Krupier pasuje.\n")); //12
        log.add(new GameLog("HIDDEN_CARD","Ukryta karta krupiera to: ")); //13
        log.add(new GameLog("BLACKJACK","BLACKJACK!\n")); //14
        log.add(new GameLog("DRAW","Remis. Otrzymujesz zwrot stawki: ")); //15
    }

    //return current time
    private String getTime() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = Calendar.getInstance().getTime();
        return df.format(currentDate);
    }

    //update log defines by code
    public void updateLog(int logCode) {
        gameLog += getTime() + ": " + log.get(logCode).getValue();
        if (logCode==2 || logCode==5) {
            gameLog += Integer.toString(gameDeposit)+"$\n";
        }
        if (logCode==15) {
            gameLog += Integer.toString(gameDeposit/2)+"$\n";
        }
    }

    //update draw player card log
    public void updateDrawCardLogPlayer(int logCode, String card) {
        gameLog += getTime() + ": " + log.get(logCode).getValue() + card+"\n";
    }

    //update draw croupier card log, restricted by game rules
    public void updateDrawCardLogCroupier(int logCode, String card, boolean isHidden) {
        if(!isHidden) {
            gameLog += getTime() + ": " + log.get(logCode).getValue() + card + "\n";
        } else {
            gameLog += getTime() + ": " + log.get(logCode).getValue() + "ZAKRYTA KARTA!\n";
        }
    }

    //return game log String
    public String getGameLog() {
        return gameLog;
    }

    //return game deposit (current cash to win)
    public int getGameDeposit() {
        return gameDeposit;
    }

    //set the game deposit
    public void setGameDeposit(int deposit) {
        this.gameDeposit = deposit;
    }

    //check is player must wait (hit wait button)
    public boolean isPlayerWait() {
        return isPlayerWait;
    }

    //set flag when player hit wait button (end turn)
    public void playerWait() {
        isPlayerWait = true;
        updateLog(9);
    }

    //check and set is croupier or player are able to game (enough cash in deposit)
    public boolean checkGameOver(int croupierDeposit, int playerDeposit) {
        if (croupierDeposit < 10) {
            updateLog(7);
            isOver=true;
        }else if (playerDeposit < 10) {
            updateLog(8);
            isOver=true;
        } else {
            isOver=false;
        }
        return isOver;
    }

    //return is game over or not
    public boolean getOver() {
        return isOver;
    }

    //set the new round after hit next round button
    public void newRound() {
        winner = 0;
        isCroupierWait = false;
        isPlayerWait = false;
        gameDeposit=0;
        gameLog="";
    }

    //reset all variables
    public void reset() {
        winner = 0;
        isCroupierWait = false;
        isPlayerWait = false;
        gameDeposit=0;
        gameLog="";
        isOver = false;
    }

}

