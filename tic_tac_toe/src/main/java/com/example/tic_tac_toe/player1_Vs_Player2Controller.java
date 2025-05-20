package com.example.tic_tac_toe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.stage.Stage;

public class player1_Vs_Player2Controller {
    @FXML private Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;
    @FXML private Label statusLabel;

    private boolean isXTurn = true;
    private String[][] board = new String[3][3];
    private Button[][] buttons;

    @FXML
    public void initialize() {
        buttons = new Button[][] {
                {btn00, btn01, btn02},
                {btn10, btn11, btn12},
                {btn20, btn21, btn22}
        };

        statusLabel.setText("X's Turn");
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = "";
    }

    @FXML
    public void ButtonOnAction(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        int row = -1, col = -1;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (clicked == buttons[i][j]) {
                    row = i;
                    col = j;
                }

        if (!clicked.getText().isEmpty()) return;

        String currentSymbol = isXTurn ? "X" : "O";
        clicked.setText(currentSymbol);
        clicked.setDisable(true);
        board[row][col] = currentSymbol;

        if (checkWin(currentSymbol)) {
            statusLabel.setText(currentSymbol + " Wins!");
            endGame();
        } else if (isDraw()) {
            statusLabel.setText("It's a Draw!");
            endGame();
        } else {
            isXTurn = !isXTurn;
            statusLabel.setText((isXTurn ? "X" : "O") + "'s Turn");
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
                if (cell.isEmpty())
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
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Tic Tac Toe Dashboard");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
