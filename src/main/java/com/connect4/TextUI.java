package com.connect4;

import com.connect4.model.Board;
import com.connect4.model.Cell;

import java.io.IOException;
import java.util.Scanner;

public class TextUI {
    private final Game game;
    private final Scanner scanner;
    private boolean exitRequested;

    public TextUI(Game game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
        this.exitRequested = false;
    }

    public void start() {
        printWelcomeMessage();

        while (!exitRequested && !game.isGameOver()) {
            displayBoard();
            handleGameTurn();
        }

        if (!exitRequested) {
            displayFinalResult();
        }
    }

    private void printWelcomeMessage() {
        System.out.println("=== Connect 4 Game ===");
        System.out.println("Parancsok:");
        System.out.println("  move <column>  - Lépés (0-" + (game.getBoard().getCols()-1) + ")");
        System.out.println("  save <file>    - Játék mentése");
        System.out.println("  load <file>    - Játék betöltése");
        System.out.println("  quit           - Kilépés");
        System.out.println("----------------------");
    }

    private void displayBoard() {
        Board board = game.getBoard();
        System.out.println("\nAktuális tábla:");

        // Display rows from top to bottom
        for (int row = board.getRows() - 1; row >= 0; row--) {
            System.out.print("|");
            for (int col = 0; col < board.getCols(); col++) {
                Cell cell = board.getCell(row, col);
                System.out.print(cell.getSymbol() + "|");
            }
            System.out.println();
        }

        // Display column numbers
        System.out.print(" ");
        for (int col = 0; col < board.getCols(); col++) {
            System.out.print(" " + col);
        }
        System.out.println("\n");
    }

    private void handleGameTurn() {
        System.out.print(" " + game.getCurrentPlayer().getSymbol() + " következik. Írj be egy parancsot: ");
        String input = scanner.nextLine().trim();
        processInput(input);
    }

    private void processInput(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length == 0) return;

        switch (parts[0].toLowerCase()) {
            case "move":
                handleMoveCommand(parts);
                break;
            case "save":
                handleSaveCommand(parts);
                break;
            case "load":
                handleLoadCommand(parts);
                break;
            case "quit":
                exitRequested = true;
                System.out.println("Kilépés ....");
                break;
            default:
                System.out.println("Hibás parancs. Elérhető parancsok: move, save, load, quit");
        }
    }

    private void handleMoveCommand(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Kérlek adj egy oszlop számot!");
            return;
        }

        try {
            int column = Integer.parseInt(parts[1]);
            if (game.isValidMove(column)) {
                game.makeMove(column);
            } else {
                System.out.println("Hibás lépés! Oszlop számnak 0-" +
                        (game.getBoard().getCols()-1) + " és nem szabad megtelt oszlopot választani");
            }
        } catch (NumberFormatException e) {
            System.out.println("Hibás oszlop szám formátum!");
        }
    }

    private void handleSaveCommand(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Fájlnév?");
            return;
        }

        try {
            FileHandler.saveBoard(game.getBoard(), parts[1]);
            System.out.println("Játék állás mentve: " + parts[1]);
        } catch (IOException e) {
            System.out.println("Hiba a játék mentése közbe: " + e.getMessage());
        }
    }

    private void handleLoadCommand(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Kérlek adj meg egy fájl nevet:");
            return;
        }

        try {
            Board loadedBoard = FileHandler.loadBoard(parts[1]);
            game.resetGame(loadedBoard.getRows(), loadedBoard.getCols());
            System.out.println("Játék betöltve: " + parts[1]);
        } catch (IOException e) {
            System.out.println("Hiba a játék betöltésénél: " + e.getMessage());
        }
    }

    private void displayFinalResult() {
        displayBoard();
        if (game.isDraw()) {
            System.out.println("=== Game Over ===");
            System.out.println("Döntetlen!");
        } else {
            System.out.println("=== Game Over ===");
            System.out.println(" " + game.getWinner().getSymbol() + " nyert!");
        }
    }
}