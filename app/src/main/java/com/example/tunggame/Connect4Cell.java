package com.example.tunggame;

public class Connect4Cell {
    /* Connect4 Cell of Board*/
    public boolean empty;
    public  Connect4Board.Turn player;

    public Connect4Cell() {
        empty = true;
    }

    public void setPlayer(Connect4Board.Turn player) {
        this.player = player;
        empty = false;
    }
}