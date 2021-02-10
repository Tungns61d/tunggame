package com.example.tunggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.AppCompatActivity;



public class PuzzleLevel extends AppCompatActivity {

    public static int LEVEL=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_puzzle_level);
        //LEVEL = 3;
    }

    public void puzzle3x3(View view){
        this.LEVEL = 3;
        Intent in = new Intent(this,Puzzle3.class);
        startActivity(in);
        finish();
    }
    public void puzzle4x4(View view){
        this.LEVEL = 4;
        Intent in = new Intent(this,Puzzle3.class);
        startActivity(in);
        finish();
    }
    public void puzzle5x5(View view){
        this.LEVEL = 5;
        Intent in = new Intent(this,Puzzle3.class);
        startActivity(in);
        finish();
    }
    /*
    public void quit(View view){
        finish();
        System.exit(0);
    }*/
}
