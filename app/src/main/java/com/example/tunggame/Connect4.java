package com.example.tunggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Connect4 extends AppCompatActivity {

    private ImageView[][] cells;
    private View boardView;
    private Connect4Board board;
    private ViewHolder viewHolder;
    private static int NUM_ROWS=6;
    private static int NUM_COLS=7;

    private class ViewHolder {
        public TextView winnerText;
        public ImageView turnIndicatorImageView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_connect4);

        //generate Board
        board=new Connect4Board(NUM_COLS,NUM_ROWS);
        boardView=findViewById(R.id.game_board);
        buildCells();

        //when we touch screen -> drop
        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_UP:{
                        int col=colAtx(event.getX());
                        if (col!=-1)
                            drop(col);
                    }
                }

                return true;
            }
        });

        ImageButton resetButton=(ImageButton) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });



        viewHolder=new ViewHolder();
        viewHolder.turnIndicatorImageView=(ImageView) findViewById(R.id.turn_indicator_image_view);
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        viewHolder.winnerText=(TextView)findViewById(R.id.winner_text);
        viewHolder.winnerText.setVisibility(View.GONE);


    }

    public void buildCells(){
        cells=new ImageView[NUM_ROWS][NUM_COLS];
        for (int r=0;r<NUM_ROWS;r++){
            ViewGroup row=(ViewGroup)((ViewGroup)boardView).getChildAt(r);
            row.setClipChildren(false);
            for (int c=0;c<NUM_COLS;c++){
                ImageView imageView=(ImageView)row.getChildAt(c);
                imageView.setImageResource(android.R.color.transparent);
                cells[r][c]=imageView;

            }
        }
    }


    //drop to the lowest row
    private void drop(int col){
        if(board.hasWinner)
            return;
        int row=board.lastAvailableRow(col);
        if(row==-1)
            return;

        final ImageView cell=cells[row][col];
        float move=-(cell.getHeight() * row + cell.getHeight() + 20); // the highest row
        cell.setY(move);
        cell.setImageResource(resourceForTurn());
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(500);
        anim.setFillAfter(true);
        cell.startAnimation(anim);
        board.occupyCell(col, row);
        if (board.checkForWin(col, row)) {
            win();
        } else {
            changeTurn();
        }
    }

    //display text and its color according winner player
    private void win() {
        //int color = board.turn == Connect4Board.Turn.FIRST ? ContextCompat.getColor(getResources(),R.color.#FF  #FF0000,null) : getResources().getColor(R.color.yellow);
        int color = board.turn == Connect4Board.Turn.FIRST ? 0xFFFF0000 : 0xFFFFFF00;
        viewHolder.winnerText.setTextColor(color);
        viewHolder.winnerText.setVisibility(View.VISIBLE);
    }

    private void changeTurn() {
        board.changeTurn();
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
    }

    private int colAtx(float x) {
        float colWidth = cells[0][0].getWidth();
        int col = (int) x / (int) colWidth;
        if (col < 0 || col > 6)
            return -1;
        return col;
    }

    private int resourceForTurn() {
        switch (board.turn) {
            case FIRST:
                return R.drawable.red;
            case SECOND:
                return R.drawable.yellow;
        }
        return R.drawable.red;
    }

    public void home(View view){
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
        finish();
    }

    private void reset() {
        board.reset();
        viewHolder.winnerText.setVisibility(View.GONE);
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        for (int r=0; r<NUM_ROWS; r++) {
            for (int c=0; c<NUM_COLS; c++) {
                cells[r][c].setImageResource(android.R.color.transparent);
            }
        }
    }
}
