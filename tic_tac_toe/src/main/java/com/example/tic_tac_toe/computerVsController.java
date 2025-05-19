package com.example.tic_tac_toe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class computerVsController {

    @FXML private Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    @FXML private Label statusLabel;

    private String[][] board = new String[3][3];
    private Button[][] buttons;
    private boolean playerTurn = true;

    @FXML
    public void initialize() {
        buttons = new Button[][] {
                {btn00, btn01, btn02},
                {btn10, btn11, btn12},
                {btn20, btn21, btn22}
        };
        statusLabel.setText("Your turn (X)");
    }

    @FXML
    public void ButtonOnAction(ActionEvent event) {
        if (!playerTurn) return;
        Button clicked = (Button) event.getSource();
        int row = -1, col = -1;
        outer: for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j] == clicked) {
                    row = i; col = j; break outer;
                }
        if (clicked.getText().isEmpty()) {
            buttons[row][col].setText("X");
            board[row][col] = "X";
            buttons[row][col].setDisable(true);
            if (checkWin("X")) {
                statusLabel.setText("You Win!");
                endGame();
            } else if (isDraw()) {
                statusLabel.setText("Draw!");
            } else {
                playerTurn = false;
                statusLabel.setText("Computer's turn (O)");
                computerMove();
            }
        }
    }

    private void computerMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    buttons[i][j].setText("O");
                    buttons[i][j].setDisable(true);
                    board[i][j] = "O";
                    if (checkWin("O")) {
                        statusLabel.setText("Computer Wins!");
                        endGame();
                    } else if (isDraw()) {
                        statusLabel.setText("Draw!");
                    } else {
                        statusLabel.setText("Your turn (X)");
                        playerTurn = true;
                    }
                    return;
                }
            }
        }
    }

    private boolean checkWin(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (symbol.equals(board[i][0]) && symbol.equals(board[i][1]) && symbol.equals(board[i][2])) return true;
            if (symbol.equals(board[0][i]) && symbol.equals(board[1][i]) && symbol.equals(board[2][i])) return true;
        }
        if (symbol.equals(board[0][0]) && symbol.equals(board[1][1]) && symbol.equals(board[2][2])) return true;
        if (symbol.equals(board[0][2]) && symbol.equals(board[1][1]) && symbol.equals(board[2][0])) return true;
        return false;
    }

    private boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == null) return false;
        return true;
    }

    private void endGame() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setDisable(true);
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/tic_tac_toe/ticTacToeDashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tic Tac Toe Dashboard");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
