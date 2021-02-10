package com.example.tunggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
//import com.example.tunggame.package.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);
    }



    public void math1s(View view) {
        Intent in = new Intent(this, Math1s.class);
        startActivity(in);
        finish();

    }

    //public static int LEVEL=3;
    public void puzzle3(View view) {
        // this.LEVEL = 3;
        Intent in = new Intent(getApplicationContext(), PuzzleLevel.class);
        startActivity(in);
        // finish();

    }

    public void connect4(View view) {
        Intent in = new Intent(this, Connect4.class);
        startActivity(in);
        finish();

    }

    public void quit(View view){

       /* System.exit(0);
        finish();*/

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}


