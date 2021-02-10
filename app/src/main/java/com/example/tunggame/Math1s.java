package com.example.tunggame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class Math1s extends AppCompatActivity {
    private ProgressBar progressBar;
    int score=0;
    public static final String  EXTRA_NUMBER="com.example.project.EXTRA_NUMBER";
    private Button correct,incorrect;
    private boolean isPause;

    private TextView txtScore, txtSum, txtOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math1s);


        initializeComponent();

        initializeData();
    }

    private void initializeComponent(){

        txtScore=(TextView)findViewById(R.id.txtScore);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        txtOperation=(TextView)findViewById(R.id.txtOperation);
        txtSum=(TextView)findViewById(R.id.txtSum);

        setupCountdown();
        //---------------------
        correct=findViewById(R.id.correct);
        incorrect=findViewById(R.id.incorrect);
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum==fakeSum){
                    isPause=false;
                    initializeData();
                    timer.start();
                    score++;
                    txtScore.setText(String.valueOf(score));

                }else{
                    isPause=true;
                    gameOver();
                    score=0;

                }

            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum!=fakeSum){
                    isPause=false;
                    initializeData();
                    timer.start();
                    score++;
                    txtScore.setText(String.valueOf(score));
                }else{
                    isPause=true;
                    gameOver();
                    score=0;
                }
            }
        });

        //----------

    }


    //Initialize countdown progressbar
    private CountDownTimer timer;
    private void setupCountdown(){
        timer =new CountDownTimer(1100,30){
            public void onTick(long millisUntilFinished) {
                if(isPause){
                    cancel();

                }
                progressBar.setProgress((int)(millisUntilFinished/10));
            }

            public void onFinish() {
                gameOver();

            }
        };
    }

    int sum=0, fakeSum=0;


    //initialize Data include 2 numbers, sum, and fakeSum, random 0,1 if 0 ->sum, else ->fakeSum
    private void initializeData(){
        int a=randomNumber();
        int b=randomNumber();
        sum=a+b;
        fakeSum=randomNumber2();
        txtOperation.setText(String.format("%s+%s",a,b));
        int randomTrueFalse =randomNumber();
        if(randomTrueFalse%2==0){
            txtSum.setText(String.format("= %s",sum));
            fakeSum=sum;
        }
        else {
            txtSum.setText(String.format("= %s",fakeSum));
        }


    }


    //function random number range 1->30
    private int randomNumber(){
        Random r=new Random();
        int i=r.nextInt(30)+1;
        return i;
    }

    //function random fakeSum range 1->50
    private int randomNumber2(){
        Random r=new Random();
        int i=r.nextInt(50)+1;
        return i;
    }

    //when game over, open game_over activity and send score
    public  void gameOver(){

        Intent intent=new Intent(getApplicationContext(),Math1sGameover.class);
        intent.putExtra(EXTRA_NUMBER,score);
        startActivity(intent);
    }


}
