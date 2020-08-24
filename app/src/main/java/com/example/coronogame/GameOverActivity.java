package com.example.coronogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends AppCompatActivity {


  private long BackPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);




        Button startGame = (Button)(findViewById(R.id.gameOverButton));
        TextView score = (TextView)(findViewById(R.id.score));
        TextView highscore = (TextView)(findViewById(R.id.highscore));
          int IntentScore = getIntent().getIntExtra("SCORE",0);

        score.setText("Score : " +  IntentScore);

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highscorekeeper  = settings.getInt("HIGH_SCORE",0);

        if(IntentScore>highscorekeeper)
        {

            highscore.setText("High Score : " + IntentScore);
            //save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE",IntentScore);
            editor.commit();

        }
        else
            {
                  highscore.setText("High Score : " +  highscorekeeper);
            }







        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)

            {

                Intent mainIntent = new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(mainIntent);

            }
        });




    }
    @Override
    public void onBackPressed() {

        if(BackPressTime + 2000 >System.currentTimeMillis())
        {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        }
        else
            {
                Toast.makeText(getBaseContext(),"Press Back Again to Exit", Toast.LENGTH_SHORT).show();

            }

        BackPressTime =System.currentTimeMillis();

    }

}