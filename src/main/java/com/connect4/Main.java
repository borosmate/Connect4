package com.connect4;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(6, 7);
        TextUI ui = new TextUI(game);
        ui.start();
    }
}