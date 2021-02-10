package com.example.tunggame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class Math1sGameover extends AppCompatActivity {
    //private Button returnGame;
    //  private Button returnHome;

    private static TextView textView1;
    private static SharedPreferences sharedPreferences;
    private static TextView bestScoreMath1s;
    private static int bestscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_math1s_gameover);
        Intent intent=getIntent();
        int score=intent.getIntExtra(Math1s.EXTRA_NUMBER,0);
        textView1=(TextView) findViewById(R.id.score);
        textView1.setText("Score: "+ score);


        bestScoreMath1s=(TextView)findViewById(R.id.bestScoreMath1s);
        sharedPreferences=getSharedPreferences("bestScoreMath1s", Context.MODE_PRIVATE);
        bestscore = sharedPreferences.getInt("bestScoreMath1s",0);
        //update best score
        setBestScore(score);

        ImageButton returnGame=(ImageButton) findViewById(R.id.return_game);
        returnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMath1s();
            }
        });
    }


    public void home(View view){
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
        finish();
    }


    public  void resetMath1s(){
        Intent intent =new Intent(this,Math1s.class);
        startActivity(intent);
        finish();
    }


    //update best score
    public static void setBestScore(int bScore){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(bScore > bestscore) {
            editor.putInt("bestScoreMath1s", bScore);
            bestScoreMath1s.setText("Best: " + bScore);
            editor.commit();
            return;
        }
        bestScoreMath1s.setText("Best: " + bestscore);
    }
}
