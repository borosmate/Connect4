package com.connect4;

import com.connect4.model.Board;
import com.connect4.model.Cell;

public class Game {
    private Board board;
    private boolean isGameOver;
    private Cell currentPlayer;
    private Cell winner;

    public Game(int rows, int cols) {
        this.board = new Board(rows, cols);
        this.currentPlayer = Cell.YELLOW;
        this.isGameOver = false;
        this.winner = Cell.EMPTY;
    }

    public void makeMove(int column) {
        if (isGameOver) {
            throw new IllegalStateException("Game is already over");
        }

        if (!isValidMove(column)) {
            throw new IllegalArgumentException("Invalid move");
        }

        Board newBoard = board.placeDisc(column, currentPlayer);
        this.board = newBoard;

        if (board.checkWin(currentPlayer)) {
            isGameOver = true;
            winner = currentPlayer;
        } else if (board.isFull()) {
            isGameOver = true;
            winner = Cell.EMPTY;
        } else {
            switchPlayer();
        }
    }

    public boolean isValidMove(int column) {
        return column >= 0 &&
                column < board.getCols() &&
                !board.isColumnFull(column);
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == Cell.YELLOW) ? Cell.RED : Cell.YELLOW;
    }

    // Getters
    public Board getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Cell getCurrentPlayer() {
        return currentPlayer;
    }

    public Cell getWinner() {
        if (!isGameOver) {
            throw new IllegalStateException("Game not finished");
        }
        return winner;
    }

    public boolean isDraw() {
        return isGameOver && winner == Cell.EMPTY;
    }

    public void resetGame(int rows, int cols) {
        this.board = new Board(rows, cols);
        this.currentPlayer = Cell.YELLOW;
        this.isGameOver = false;
        this.winner = Cell.EMPTY;
    }
}