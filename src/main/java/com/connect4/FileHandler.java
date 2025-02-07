package com.connect4;

import com.connect4.model.Board;
import com.connect4.model.Cell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileHandler {
    public static Board loadBoard(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filename));
        // Parse file and create board
        return new Board(6, 7); // Simplified
    }

    public static void saveBoard(Board board, String filename) throws IOException {
        // Convert board to string representation
        Files.writeString(Path.of(filename), board.toString());
    }
}