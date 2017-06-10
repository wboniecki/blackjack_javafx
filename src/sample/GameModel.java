package sample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Game model
 */
public class GameModel {
    private String gameLog;
    private List<GameLog> log = new ArrayList<>();
    private int gameDeposit;
    private boolean isPlayerWait = false;
    private boolean isCroupierWait = false;
    private int winner = 0;
    private boolean isOver = false;
    //0-none
    //1-player
    //2-croupier
    //3-draw

    public boolean isCroupierWait(int score) {
        if (score<17 && winner!=2) {
            isCroupierWait = false;
        } else {
            isCroupierWait = true;
        }
        return isCroupierWait;
    }

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

    public GameModel() {
        gameLog = "";
        gameDeposit = 0;
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

    private String getTime() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = Calendar.getInstance().getTime();
        return df.format(currentDate);
    }

    public void updateLog(int logCode) {
        gameLog += getTime() + ": " + log.get(logCode).getValue();
        if (logCode==2 || logCode==5) {
            gameLog += Integer.toString(gameDeposit)+"$\n";
        }
        if (logCode==15) {
            gameLog += Integer.toString(gameDeposit/2)+"$\n";
        }
    }

    public void updateDrawCardLogPlayer(int logCode, String card) {
        gameLog += getTime() + ": " + log.get(logCode).getValue() + card+"\n";
    }

    public void updateDrawCardLogCroupier(int logCode, String card, boolean isHidden) {
        if(!isHidden) {
            gameLog += getTime() + ": " + log.get(logCode).getValue() + card + "\n";
        } else {
            gameLog += getTime() + ": " + log.get(logCode).getValue() + "ZAKRYTA KARTA!\n";
        }
    }

    public String getGameLog() {
        return gameLog;
    }

    public int getGameDeposit() {
        return gameDeposit;
    }

    public void setGameDeposit(int deposit) {
        this.gameDeposit = deposit;
    }


    public boolean isPlayerWait() {
        return isPlayerWait;
    }

    public void playerWait() {
        isPlayerWait = true;
        updateLog(9);
    }

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

    public boolean getOver() {
        return isOver;
    }

    public void newRound() {
        winner = 0;
        isCroupierWait = false;
        isPlayerWait = false;
        gameDeposit=0;
        gameLog="";
    }


    public void reset() {
        winner = 0;
        isCroupierWait = false;
        isPlayerWait = false;
        gameDeposit=0;
        gameLog="";
        isOver = false;
    }

}

