package com.example.tunggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;


public class PuzzleBoard {
    /* Board of puzzle game*/
    public static int NUM_TILES = PuzzleLevel.LEVEL;
    /*definite the neighbour of each tiles*/
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private int step;
    private PuzzleBoard previousBoard;
    private ArrayList<PuzzleTile> tiles;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        NUM_TILES = PuzzleLevel.LEVEL;
        step=0;
        previousBoard = null;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,parentWidth,parentWidth,true);
        tiles = new ArrayList<>();

        int blockWidth = parentWidth/NUM_TILES;

        //load the map
        for (int x = 0; x < NUM_TILES; x++){
            for( int y = 0; y< NUM_TILES; y++){

                if( ((x*NUM_TILES)+y) == (NUM_TILES*NUM_TILES-1) ){
                    //last tile should be NULL
                    tiles.add(null);
                }
                else{
                    Bitmap tileBitmap = Bitmap.createBitmap(scaledBitmap, y*blockWidth, x*blockWidth, blockWidth, blockWidth);
                    PuzzleTile tile = new PuzzleTile(tileBitmap, (x*NUM_TILES)+y );
                    tiles.add(tile);
                }
            }
        }
    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        previousBoard = otherBoard;
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        this.step = otherBoard.step + 1;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return tiles.equals(((PuzzleBoard) obj).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    //check if sloved
    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    //Used in Class PuzzleBoardView to shuffle the board.
    //determine neighbours
    public ArrayList<PuzzleBoard> neighbours() {

        int xpos = -1,ypos = -1;
        for (int i=0;i < (NUM_TILES*NUM_TILES);i++ ){
            if( tiles.get(i) == null ){
                xpos = i%NUM_TILES;
                ypos = i/NUM_TILES;
                break;
            }
        }
        ArrayList<PuzzleBoard> possibleBoards = new ArrayList<>();
        for (int []delta : NEIGHBOUR_COORDS ) {
            int newX = xpos + delta[0];
            int newY = ypos + delta[1];
            if (newX >= 0 && newX < NUM_TILES && newY >= 0 && newY < NUM_TILES) {
                PuzzleBoard newBoard = new PuzzleBoard(this);
                newBoard.swapTiles(XYtoIndex(newX, newY), XYtoIndex(xpos, ypos));
                possibleBoards.add(newBoard);
            }
        }

        return possibleBoards;
    }



}