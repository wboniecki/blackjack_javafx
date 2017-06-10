package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewController {

    @FXML
    Button newGameButton;
    @FXML
    Label deckCountView;
    @FXML
    Button drawCard;
    @FXML
    Button bet;
    @FXML
    TextField betText;
    @FXML
    Label playerDepositView;
    @FXML
    Label croupierDepositView;
    @FXML
    TextArea systemLogView;
    @FXML
    Label gameDepositView;
    @FXML
    Button wait;
    @FXML
    Label playerScoreView;
    @FXML
    Label croupierScoreView;
    @FXML
    TextArea playerCardOwnView;
    @FXML
    TextArea croupierCardOwnView;
    @FXML
    Button newRoundButton;

    GameController gameController = new GameController();

    @FXML
    private void initialize() {
        newGameButton.setOnMouseClicked(event -> {
            gameController.setNewGame();
            newGame();
        });
        updateView();

    }

    private void updateView() {
        systemLogView.setText(gameController.getGameLog());
        deckCountView.setText(gameController.getDeckCount());
        playerDepositView.setText(gameController.getPlayerDeposit());
        croupierDepositView.setText(gameController.getCroupierDeposit());
        gameDepositView.setText(gameController.getGameDeposit());
        playerScoreView.setText(gameController.getPlayerScore());
        if(!gameController.isPlayerWait()) {
            croupierScoreView.setText(gameController.getCroupierScore());
        } else {
            croupierScoreView.setText(gameController.getCroupierFullScore());
        }
        playerCardOwnView.setText(gameController.getPlayerCardOwn());
        croupierCardOwnView.setText(gameController.getCroupierCardOwn());

        if(gameController.isPlayerWait()) {
            drawCard.setDisable(true);
            wait.setDisable(true);
            newRoundButton.setDisable(false);
        }
        if(gameController.isGameOver()) {
            newRoundButton.setDisable(true);
        }
    }
    private void newGame() {
        //ustawiamy początkowy widok parametrów gry
        drawCard.setDisable(true);
        wait.setDisable(true);
        newRoundButton.setDisable(true);
        betText.setDisable(false);
        betText.setText("50");
        bet.setDisable(false);
        updateView();

        bet.setOnMouseClicked(event -> {
            if(gameController.checkBet(betText.getText())) {
                gameController.roundStart(betText.getText());
                betText.setDisable(true);
                bet.setDisable(true);
                GameLoop();
            } else {
                updateView();
            }
        });
    }

    private void GameLoop() {
        drawCard.setDisable(false);
        wait.setDisable(false);
        updateView();
        wait.setOnMouseClicked(event -> {
            gameController.playerWait();
            updateView();
        });
        drawCard.setOnMouseClicked(event -> {
            gameController.drawPlayerCard();
            gameController.checkWin();
            updateView();
        });
        newRoundButton.setOnMouseClicked(event -> {
            gameController.setNewRound();
            newGame();
        });
    }
}
