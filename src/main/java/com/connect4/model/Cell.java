package com.connect4.model;

public enum Cell {
    EMPTY(" "),
    RED("P"),
    YELLOW("S");

    private final String symbol;

    Cell(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public Cell getOpponent() {
        if (this == RED) return YELLOW;
        if (this == YELLOW) return RED;
        throw new IllegalStateException("Empty cell has no opponent");
    }

    public static Cell fromSymbol(String symbol) {
        return switch (symbol.toUpperCase()) {
            case "P" -> RED;
            case "S" -> YELLOW;
            default -> EMPTY;
        };
    }

    @Override
    public String toString() {
        return symbol;
    }
}