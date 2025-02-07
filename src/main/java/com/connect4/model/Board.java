package com.connect4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;
import java.util.stream.IntStream;

public class Board {
    private final int rows;
    private final int cols;
    private final List<List<Cell>> columns;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.columns = createEmptyColumns();
    }

    private Board(int rows, int cols, List<List<Cell>> columns) {
        this.rows = rows;
        this.cols = cols;
        this.columns = deepCopyColumns(columns);
    }

    private List<List<Cell>> createEmptyColumns() {
        List<List<Cell>> cols = new ArrayList<>();
        for (int i = 0; i < this.cols; i++) {
            cols.add(new ArrayList<>(Collections.nCopies(rows, Cell.EMPTY)));
        }
        return cols;
    }

    private List<List<Cell>> deepCopyColumns(List<List<Cell>> original) {
        List<List<Cell>> copy = new ArrayList<>();
        for (List<Cell> column : original) {
            copy.add(new ArrayList<>(column));
        }
        return copy;
    }

    public Board placeDisc(int column, Cell color) {
        if (column < 0 || column >= cols) {
            throw new IllegalArgumentException("Hibás oszlop: " + column);
        }

        List<Cell> selectedColumn = new ArrayList<>(columns.get(column));
        int firstEmpty = selectedColumn.lastIndexOf(Cell.EMPTY);

        if (firstEmpty == -1) {
            throw new IllegalArgumentException("Oszlop tele: " + column);
        }

        selectedColumn.set(firstEmpty, color);
        List<List<Cell>> newColumns = new ArrayList<>(columns);
        newColumns.set(column, selectedColumn);

        return new Board(rows, cols, newColumns);
    }

    public boolean checkWin(Cell color) {
        return checkHorizontal(color) ||
                checkVertical(color) ||
                checkDiagonalDown(color) ||
                checkDiagonalUp(color);
    }

    private boolean checkHorizontal(Cell color) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (checkConsecutive(row, col, 0, 1, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical(Cell color) {
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                if (checkConsecutive(row, col, 1, 0, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalDown(Cell color) {
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (checkConsecutive(row, col, 1, 1, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalUp(Cell color) {
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (checkConsecutive(row, col, -1, 1, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkConsecutive(int startRow, int startCol,
                                     int rowStep, int colStep, Cell color) {
        for (int i = 0; i < 4; i++) {
            int currentRow = startRow + (i * rowStep);
            int currentCol = startCol + (i * colStep);
            if (getCell(currentRow, currentCol) != color) {
                return false;
            }
        }
        return true;
    }

    public Cell getCell(int row, int col) {
        return columns.get(col).get(row);
    }

    public boolean isColumnFull(int column) {
        return columns.get(column).stream()
                .noneMatch(cell -> cell == Cell.EMPTY);
    }

    public boolean isFull() {
        return IntStream.range(0, cols).allMatch(this::isColumnFull);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}