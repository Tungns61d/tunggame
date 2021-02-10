package com.example.tunggame;

public class Connect4Board {
    public com.example.tunggame.Connect4Board.Turn turn;
    /* Connect4 Board object information*/
    private int numCols;
    private int numRows;
    public boolean hasWinner;
    public Connect4Cell[][] cells;

    public enum Turn {
        FIRST, SECOND
    }

    public Connect4Board(int cols, int rows) {
        numCols = cols;
        numRows = rows;
        cells = new Connect4Cell[numCols][numRows];
        reset();
    }

    public void reset() {
        hasWinner = false;
        turn = Turn.FIRST;
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                cells [col][row] = new Connect4Cell();
            }
        }
    }

    public int lastAvailableRow(int col) {
        for (int row = numRows - 1; row >= 0; row--) {
            if (cells[col][row].empty) {
                return row;
            }
        }
        return -1;
    }

    public void occupyCell(int col, int row) {
        cells[col][row].setPlayer(turn);
    }

    //change turn
    public void changeTurn() {
        if (turn == Turn.FIRST) {
            turn = Turn.SECOND;
        } else {
            turn = Turn.FIRST;
        }
    }

    public boolean checkForWin(int c, int r) {
        for (int col = 0; col < numCols; col++) {
            if (isConnected(turn, 0, 1, col, 0, 0) || isConnected(turn, 1, 1, col, 0, 0) || isConnected(turn, -1, 1, col, 0, 0)) {
                hasWinner = true;
                return true;
            }
        }
        for (int row = 0; row < numRows; row++) {
            if (isConnected(turn, 1, 0, 0, row, 0) || isConnected(turn, 1, 1, 0, row, 0) || isConnected(turn, -1, 1, numCols - 1, row, 0)) {
                hasWinner = true;
                return true;
            }
        }
        return false;
    }

    //check if 4 connected
    private boolean isConnected(Turn player, int dirX, int dirY, int col, int row, int count) {
        if (count >= 4) {
            return true;
        }
        if (col < 0 || col >= numCols || row < 0 || row >= numRows) {
            return false;
        }
        Connect4Cell cell = cells[col][row];
        if (cell.player == player) {
            return isConnected(player, dirX, dirY, col + dirX, row + dirY, count + 1);
        } else {
            return isConnected(player, dirX, dirY, col + dirX, row + dirY, 0);
        }
    }

}
