package com.example.tunggame;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

public class PuzzleBoardView extends View {
    //public static final int NUM_SHUFFLE_STEPS = 40;
    public int Score;

    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();

    public PuzzleBoardView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
        Score = -1;
    }

    //display the game's image
    public void initialize(Bitmap imageBitmap) {

        this.invalidate();
        int width = getMeasuredWidth();

        puzzleBoard = new PuzzleBoard(imageBitmap, width);
        shuffle();
        shuffle();
        shuffle();
        shuffle();
        shuffle();
        shuffle();
    }

    @Override

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Puzzle3.setScore(Score);

        puzzleBoard.draw(canvas);

    }

    public void shuffle() {
        if (animation == null && puzzleBoard != null) {
            // Do something. Then:
            Score = 0;

            for(int i=0; i< 25;i++){
                ArrayList<PuzzleBoard> neighbours = puzzleBoard.neighbours(); //get possible board form this current board
                int randIndex = random.nextInt( neighbours.size() );
                //may be useful in wrost case
                while ( neighbours.get(randIndex).resolved() == true ){//loai nhung o da chiem position
                    randIndex = (randIndex+1) % (neighbours.size() );
                }
                puzzleBoard = neighbours.get(randIndex);   //select random board
            }
            invalidate();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation == null && puzzleBoard != null) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if(Score != -1){
                            Score++;
                        }
                        if (puzzleBoard.resolved()) {
                            Puzzle3.setBestScore(Score);//update best score

                            Score = -1;// if solved-> reset score to -1
                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(event);
    }


}
