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
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = null;
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
                computerMovement();
            }
        }
    }

    private void computerMovement() {
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

    private boolean checkWin(String s) {
        for (int i = 0; i < 3; i++)
            if ((s.equals(board[i][0]) && s.equals(board[i][1]) && s.equals(board[i][2])) ||
                    (s.equals(board[0][i]) && s.equals(board[1][i]) && s.equals(board[2][i])))
                return true;

        return (s.equals(board[0][0]) && s.equals(board[1][1]) && s.equals(board[2][2])) ||
                (s.equals(board[0][2]) && s.equals(board[1][1]) && s.equals(board[2][0]));
    }

    private boolean isDraw() {
        for (String[] row : board)
            for (String cell : row)
                if (cell == null)
                    return false;
        return true;
    }

    private void endGame() {
        for (Button[] row : buttons)
            for (Button b : row)
                b.setDisable(true);
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
